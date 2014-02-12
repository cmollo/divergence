(ns divergence.component)


(defn component [name attributes]
  {:name name
   :attr attributes})

(defn sprite [texture]
  (component :sprite
             {:texture texture}))

(defn on-stage [stage]
  (component :stage stage))

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

(defn named [n]
  (component :name n))

(def create-ref
  "Create a reference to the PIXI object"
  (component :create-ref true))

(def player-input
  (component :player-input true))

(def has-actions
  (component :actions #{}))

(def movable
  (component :velocity {:x-speed 0 :y-speed 0 :rot-speed 0}))

(defn text [string style]
  (component :text
             {:string string
              :style style}))

(def fps-counter
  (component :fps-counter true))
