(ns divergence.core)

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

(aset (.-anchor bunny) "x" .5)
(aset (.-anchor bunny) "y" .5)

;(.addChild stage bunny)
;(.removeChild stage bunny)

(defn sprite [texture]
  {:sprite-texture texture
   :ref nil})

(defn on-stage []
  {:on-stage true})

(defn position [x y rot]
  {:x x :y y :rot rot})

(defn throw-in-space []
  {:x-speed 0.5 :y-speed 0 :rot-speed 0.1})

(defn move [{:keys [x-speed y-speed rot-speed ref] :as entity}]
  (aset ref "rotation" (+ rot-speed (.-rotation ref)))
  (aset (.-position ref) "x" (mod (+ 0.5 (.-x ref.position)) 480)))

(defn position-setter [entity]
  (let [{:keys [ref x y rot]} entity]
    (aset (.-position ref) "x" x)
    (aset (.-position ref) "y" y)
    (aset ref "rotation" rot)
    entity))

(defn sprite-system [entities]
  (for [e entities]
    (assoc e :ref (js/PIXI.Sprite. (:sprite-texture e)))))

(defn on-stage-system [stage entities]
  (doseq [e entities]
    (when (:on-stage e)
      (.addChild stage (:ref e))))
  entities)

(defn entity [name components]
  (apply merge {:name name} components))

(def bunny-entity
  (entity :bunny [(sprite bunnyTexture)
                  (position 50 50 0)
                  (on-stage)
                  (throw-in-space)]))

(def entities (atom [bunny-entity]))

(def animate-ref (atom nil) )

(defn setup [entities stage]
  (->>
   entities
   (sprite-system)
   (on-stage-system stage)
   (map position-setter)
   ))

(reset! entities (setup @entities stage))
(first @entities)

(map position-system @entities)

entities

#_(on-stage-system [{:on-stage true :ref bunny}] stage)

(defn animate []
  (aset bunny "rotation" (+ 0.01 (.-rotation bunny)))
  (aset (.-position bunny) "x" (mod (+ 0.5 (.-x bunny.position)) 480))
  (.render renderer stage)
  (js/requestAnimationFrame @animate-ref))

(setup stage)

(reset! animate-ref animate)
(js/requestAnimationFrame @animate-ref)

(comment
  (reset! animate-ref animate)
  @animate-ref
 )
