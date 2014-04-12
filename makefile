JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Point.java \
	ReadPointsFile.java \
	ClosestPair.java \
	ComputeClosestPair.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
