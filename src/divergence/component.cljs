(ns divergence.component)

(defn component [name attributes]
  "Simple returns a hashmap of the name and attributes"
  {:name name
   :attr attributes})

(defn sprite [texture]
  (component :sprite
             {:texture texture}))

(defn tiling-sprite [texture]
  (component :tiling-sprite
             {:texture texture}))

(defn on-stage [stage]
  (component :stage stage))

(defn position [x y rot]
  (component :position
             [x y rot]))

(defn friction [f]
  (component :friction [f]))

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
  (component :velocity [0 0 0]))

(def pushable
  (component :pushable true))

(defn text [string style]
  (component :text
             {:string string
              :style style}))

(def fps-counter
  (component :fps-counter true))

(def collidable
  (component :collidable :true))

(def accelerates
  (component :acceleration [0 0 0]))

(defn gravity
  "Gravity settings should be:
   [x-acceleration y-acceleration rot-acceleration]"
  [gravity-settings]
  (component :gravity gravity-settings))
