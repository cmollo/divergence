(ns divergence.leveleditor
  (:require [divergence.component :as c]
            [divergence.entity :as e]))

(def canvas (js/document.getElementById ""))

(defn set-entity [x y id]
  (case id
    0 (entities conj)
    1 ()
    2 ()
    3 ()
    )

(defn parse-click [event]
	(let [cx (.-pageX event)
		  cy (.-pageY event)
		  offset (.offset canvas)
		  left-offset (.-left offset)
		  top-offset (.-top offset)
		  x (- cx left-offset)
		  y (- cy top-offset)
		  height (.height canvas)
		  width (.width canvas)
		]
    (set-entity x y 0)
    ))


(defn set-click []
  (set! (.-onclick js/document) parse-click))
