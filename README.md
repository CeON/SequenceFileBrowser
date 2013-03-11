SequenceFile Browser
====================

This is just a small tool that allows SequenceFile Browsing.

## UI
Topmost text box allows you to specify SequenceFile URI (e.g. hdfs://hadoop-master:8020/user/sbd/file.sq) and open it.
Two panes show one key-value pair. It also let you specify Interpreters for the values. Bottommost controls let you go
to the next record and sync in a SequenceFile.

## Interpreters
Interpreter is a class converting any Writable stored in a SequenceFile to a string that an application will display.

We are providing some most basic interpreters:
 - BasicInterpreter - calls toString on a Writable
 - EncodedStringInterpreter - assumes it gets BytesWritable with string encoded in UTF-8
 - ProtoBufInterpreter - uses specified class to deserialise ProtoBuf message stored in a writable
 - XmlInterpreter - assumes a string stored in writable is XML and pretty-prints it

## Custim interpreters
All interpreters should extend  pl.edu.icm.tools.sequencefile.browser.interpreter.Interpreter trait.
Additionally they may contain multiple setAnything(s: String): Unit
methods which describe class properties that can be set during runtime. After setting all properties, the
setup(): Unit method is called. To interpret a single writable, interpret(writable: Writable): String is called.

## Configuration
We use TypeSafe's Config. You can provide configuration options as JVM parameters or in application.config file.

Example config:

    #SOCKS server to be used by application
    socks.server="127.0.0.1:8889"
    #hints in interpreter selection combo
    interpreter.hints=["package.name.SomeInterpreter", "package.name.AnotherInterpreter"]


