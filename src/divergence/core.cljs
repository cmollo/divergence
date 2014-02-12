(ns divergence.core
  (:require [divergence.component :as c]
            [divergence.entity :as e]
            [divergence.system :as s]))

(enable-console-print!)

(def renderer (js/PIXI.autoDetectRenderer. 400 300))
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


(def entities [(e/bunny stage) (e/some-text stage)])

(defn setup []
  ;; Register all the entities in our maps
  (doseq [e entities] (register-entity! e))
  (let [c->e @component->entities]
    (s/create-ref (c->e :sprite))
    (s/create-text (c->e :text))
    (s/on-stage (c->e :stage))
    (s/position (c->e :position))
    (s/anchor (c->e :anchor))
    (s/scale (c->e :scale))))


(defn animate []
  (let [c->e @component->entities]
    (.render renderer stage)
    (s/player-input (c->e :player-input))
    (s/execute-actions (c->e :actions))
    (s/move (c->e :velocity))
    (s/fps-counter (c->e :fps-counter))
    (js/requestAnimationFrame @animate-ref)))

(reset! animate-ref animate)

(setup)
(js/requestAnimationFrame @animate-ref)
