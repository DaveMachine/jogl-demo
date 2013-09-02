(defproject jogl-demo "0.1.0-SNAPSHOT"
  :description "Java OpenGL demo"
  :url "https://github.com/DaveMachine/jogl-demo"
  :license {:name "Unlicensed"
            :url "http://unlicense.org"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.jogamp.jogl/jogl-all "2.0.2"]
                 [org.jogamp.gluegen/gluegen-rt-main "2.0.2"]]
  :main jogl-demo.core
  :aot [jogl-demo.core])
