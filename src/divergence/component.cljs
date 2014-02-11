(ns divergence.component)


(defn component [name attributes]
  {:name name
   :attr attributes})

(defn sprite [texture]
  (component :sprite
             {:texture texture}))

(defn on-stage [stage]
  (component :on-stage stage))

(defn position [x y rot]
  (component :position
             {:x x :y y :rot rot}))

(defn throw-in-space []
  (component
   :velocity
   {:x-speed 0.5 :y-speed 0 :rot-speed 0.01}))

(defn anchor [x y]
  (component :anchor
             {:x x :y y}))

(defn scale [x y]
  (component :scale
             {:x-scale x :y-scale y}))

(defn create-ref
  "Create a reference to the PIXI object"
  [texture-type]
  (component :create-ref true))
