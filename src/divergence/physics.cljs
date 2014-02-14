(ns divergence.physics)

(defn colliding? [e1 e2]
  (let [{[x1 y1 _] :position ref1 :ref} e1
        {[x2 y2 _] :position ref2 :ref} e2
        w1 (.-width ref1) h1 (.-height ref1)
        w2 (.-width ref2) h2 (.-height ref2)]
    (js/physics.isColliding x1 y1 w1 h1
                            x2 y2 w2 h2)))
