(ns divergence.entity
  (:require [divergence.component :as c]))

(def bunnyTexture (js/PIXI.Texture.fromImage "assets/img/bunny.png"))
(def blockTexture (js/PIXI.Texture.fromImage "assets/img/Brick_Block.png"))
(def boxTexture (js/PIXI.Texture.fromImage "assets/img/box.png"))

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
           (c/position 90 50 0)
           (c/on-stage stage)
           (c/friction 1)
           c/collidable
           (c/scale 2 2)
           c/accelerates
           (c/gravity [0 .2 0])
           ]))

(defn block [scale-x scale-y x y stage]
  (entity [(c/named :block)
           (c/sprite blockTexture)
           c/create-ref
           (c/position x y 0)
           (c/scale scale-x scale-y)
           c/collidable
           (c/friction 5)
           (c/on-stage stage)]))


(defn box [x y stage]
  (entity [(c/named :box)
           (c/sprite boxTexture)
           c/create-ref
           c/accelerates
           c/movable
           c/has-actions
           c/pushable
           c/collidable
           (c/friction 1)
           (c/gravity [0 .2 0])
           (c/position x y 0)
           (c/on-stage stage)
           (c/scale .5 .5)
           ]))

(def horizontal-full-block
  (partial block 2 .1))

(def vertical-full-block
  (partial block .1 1.5))

(defn some-text [stage]
  (entity [(c/named :fps-counter)
           (c/text "Hello World" #js {:font "20px Arial" :fill "black"})
           (c/position 20 10 0)
           c/fps-counter
           (c/on-stage stage)]))

