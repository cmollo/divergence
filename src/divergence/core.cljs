(ns divergence.core
  (:require [divergence.component :as c]
            [divergence.entity :as e]
            [divergence.system :as s]
            [divergence.system.time-travel :as tt]))

(enable-console-print!)

(def renderer (js/PIXI.autoDetectRenderer. 800 600))
(js/document.body.appendChild (.-view renderer))

(def stage (js/PIXI.Stage. 0x66FF99))

(def entity->components
  "A map to an entity and a list of it's components"
  (atom {}))

(def component->entities
  "A map to a component and a list of entities that use it"
  (atom {}))

(def entity-count (atom 0))

(def animate-ref (atom nil) )

(defn register-entity! [entity]
  (let [entity-atom (atom entity)]
    (swap! entity->components assoc @entity-count entity-atom)
    (swap! entity-count inc)
    (doseq [[n component] entity]
      (swap! component->entities update-in [n] conj entity-atom))))


(def entities
  [(e/bunny stage)
   (e/some-text stage)
   (e/vertical-full-block 0 -40 stage)
   (e/vertical-full-block 760 -40 stage)
   (e/horizontal-full-block 0 560 stage)
   (e/timestream)
   ])

(defn setup [entities]
  ;; Register all the entities in our maps
  (doseq [e entities] (register-entity! e))
  (let [c->e @component->entities]
    (s/create-ref (c->e :sprite))
    (s/create-tiling-ref (c->e :tiling-sprite))
    (s/create-text (c->e :text))
    (s/on-stage (c->e :stage))
    (s/position (c->e :position))
    (s/anchor (c->e :anchor))
    (s/scale (c->e :scale))

    ;; Setup the time travel
    (let [timestream (first (c->e :timestream))]
      (tt/save-entities-to-timestream! timestream [(@entity->components 0)])
      (swap! timestream assoc-in [:timestream :prev-node] [0 0]))

    ))


(defn animate []
  (let [c->e @component->entities]
    (.render renderer stage)

    ;; Time travel
    (let [timestream (first (c->e :timestream))]
      ;; TODO fix this so it works for all entities, I'm just being lazy here
      (tt/time-tick timestream [(@entity->components 0)])

      )

    #_(println (:position @(first (c->e :player-input))))

    (s/player-input (c->e :player-input))
    (s/execute-actions (c->e :actions))
    (s/gravity (c->e :gravity))
    (s/movement-caps (c->e :velocity))
    (s/friction (c->e :acceleration))
    (s/accelerate (c->e :acceleration))
    (s/collide (c->e :collidable))
    (s/move (c->e :velocity))
    (s/position (c->e :position))

    ;; FPS counter
    (s/fps-counter (c->e :fps-counter))

    (js/requestAnimationFrame @animate-ref)))


(reset! animate-ref animate)

(setup entities)
(js/requestAnimationFrame @animate-ref)


(comment
  (@entity->components 0)

  (def timestream (get-in @(first (@component->entities :timestream)) [:timestream :timestream]))

  (get-in timestream [0 1598])
  (tt/reverse-time timestream 0 1598 1)


  (count (get-in @(first (@component->entities :timestream)) [:timestream :timestream 0]))
  (map :prev-node (get-in @(first (@component->entities :timestream)) [:timestream :timestream 536]))
 )
