(ns divergence.entity
  (:require [divergence.component :as c]))

(def bunnyTexture (js/PIXI.Texture.fromImage "assets/img/bunny.png"))

(defn entity [components]
  (reduce
   (fn [acc component]
     (assoc acc (:name component) (:attr component)))
   {}
   components))


(defn bunny [stage]
  (entity [(c/named :bunny)
           (c/sprite bunnyTexture)
           c/create-ref
           c/player-input
           c/has-actions
           c/movable
           (c/position 50 100 0)
           (c/on-stage stage)
           (c/anchor 0.5 0.5)
           (c/scale 2 2)]))

(defn some-text [stage]
  (entity [(c/named :fps-counter)
           (c/text "Hello World" #js {:font "20px Arial" :fill "black"})
           (c/position 20 10 0)
           c/fps-counter
           (c/on-stage stage)]))
