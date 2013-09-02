(ns jogl-demo.core
  (:use [jogl-demo.demo :as demo])
  (:gen-class))

(defn -main
  "I don't do a whole lot."
  [& args]
  (demo/main-swing))
