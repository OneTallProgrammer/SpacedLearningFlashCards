#!/bin/bash

javac -Xlint:unchecked -cp out/ -d out java/model/*.java
javac -Xlint:unchecked -cp out/ -d out java/parsing/*.java

javac -Xlint:unchecked -cp out/ -d out java/SpacedLearning.java
