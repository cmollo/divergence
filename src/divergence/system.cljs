(ns divergence.system)

(defn as [entity k]
  (@entity k))

(defn move [entities]
  (doseq [e entities]
    (let [{{:keys [x-speed y-speed rot-speed] :as attrs} :velocity ref :ref} @e]
      (aset ref "rotation" (+ rot-speed (.-rotation ref)))
      (aset (.-position ref) "x" (mod (+ x-speed (.-x (.-position ref))) 480))
      (aset (.-position ref) "y" (mod (+ y-speed (.-y (.-position ref))) 480)))))

(defn anchor [entities]
  (doseq [e entities]
    (let [{:keys [x y]} (@e :anchor) {ref :ref} @e]
      (aset (.-anchor ref) "x" x)
      (aset (.-anchor ref) "y" y))))

(defn scale [entities]
  (doseq [e entities]
    (let [{{:keys [x-scale y-scale rot-speed]} :scale ref :ref} @e]
      (aset (.-scale ref) "x" x-scale)
      (aset (.-scale ref) "y" y-scale))))

(defn position [entities]
  (doseq [e entities]
    (let [{{:keys [x y rot]} :position ref :ref} @e]
      (aset (.-position ref) "x" x)
      (aset (.-position ref) "y" y)
      (aset ref "rotation" rot))))

(defn create-ref [entities]
  (doseq [e entities]
    (swap! e assoc :ref (js/PIXI.Sprite. (-> @e :sprite :texture)))))

(defn player-input [entities]
  (doseq [e entities]))

(defn on-stage [entities]
  (doseq [e entities]
    (.addChild (@e :stage) (@e :ref))))

(def code->key
  {37 :left
   38 :up
   39 :right
   40 :down})

(def key-inputs (atom #{}))

(aset js/document.body "onkeydown" (fn [e]
                                     (let [k (code->key (.-keyCode e))]
                                       (when k
                                         (swap! key-inputs conj k)))))

(aset js/document.body "onkeyup" (fn [e]
                                     (let [k (code->key (.-keyCode e))]
                                       (when k
                                         (swap! key-inputs disj k)))))

(defn player-input [entities]
  (doseq [e entities]
    (swap! e assoc-in [:actions] @key-inputs)))

(defn execute-actions [entities]
  (doseq [e entities
          :let [actions (@e :actions)]]
    (when actions
      (cond
       (actions :left) (swap! e assoc-in [:velocity :x-speed] -2)
       (actions :up) (swap! e assoc-in [:velocity :y-speed] -2)
       (actions :right) (swap! e assoc-in [:velocity :x-speed] 2)
       (actions :down) (swap! e assoc-in [:velocity :y-speed] 2)
       :else (do (swap! e assoc-in [:velocity :x-speed] 0)
                 (swap! e assoc-in [:velocity :y-speed] 0))))))


(defn create-text [entities]
  (doseq [e entities]
    (let [style (get-in @e [:text :style])
          text (get-in @e [:text :string])]
      (swap! e assoc :ref (js/PIXI.Text. text style)))))

(def fps-time (atom (.getTime (js/Date.))))
(defn fps-counter [entities]
  (doseq [e entities
          :let [ref (@e :ref)
                now (.getTime (js/Date.))]]
    (.setText ref (str "FPS: " (js/Math.round (/ 1000 (- now @fps-time))))))
  (reset! fps-time (.getTime (js/Date.))))
