JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Location.java \
	Person.java \
    Main.java 
default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class