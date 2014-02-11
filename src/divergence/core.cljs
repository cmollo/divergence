(ns divergence.core
  (:require [divergence.component :as c]
            [divergence.system :as s]))

(enable-console-print!)

(println "Hello world!")

(def renderer (js/PIXI.autoDetectRenderer. 400 300))
(js/document.body.appendChild (.-view renderer))

(def stage (js/PIXI.Stage. 0x66FF99))

(def bunnyTexture (js/PIXI.Texture.fromImage "assets/img/bunny.png"))
(def bunny (js/PIXI.Sprite. bunnyTexture))

(aset (.-position bunny) "x" 180)
(aset (.-position bunny) "y" 150)
(aset (.-scale bunny) "x" 2)
(aset (.-scale bunny) "y" 2)

;(aset (.-anchor bunny) "x" .5)
;(aset (.-anchor bunny) "y" .5)

;(.addChild stage bunny)
;(.removeChild stage bunny)

(def entity->components
  "A map to an entity and a list of it's components"
  (atom {}))

(def component->entities
  "A map to a component and a list of entities that use it"
  (atom {}))

(defn entity [name components]
  (apply merge {:name name} components))

(hash :rabbit)

(def bunny-entity
  (entity :bunny [(sprite bunnyTexture)
                  (position 50 100 0)
                  (on-stage)
                  (anchor 0.5 0.5)
                  (throw-in-space)
                  (scale 2 2)]))

(def entities (atom [bunny-entity]))

(def animate-ref (atom nil) )

(defn setup [entities stage]
  (->>
   entities
   (sprite-system)
   (on-stage-system stage)
   (map position-setter)
   (map anchor-system)
   (map scale-system)
   ))


(defn animate []
  (move (first @entities))
  (.render renderer stage)
  (js/requestAnimationFrame @animate-ref))

(reset! entities (setup @entities stage))
(reset! animate-ref animate)
(js/requestAnimationFrame @animate-ref)

(comment
  (reset! animate-ref animate)
  @animate-ref
 )
