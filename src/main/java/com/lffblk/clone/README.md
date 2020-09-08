Deep copy algorithm implementation with Java Reflection ([entry point](CopyUtils.java)).

Following functionality is supported:
1. Copying of arrays, collections (`ArrayList`, `LinkedList` and `HashSet` are tested) and maps: as a source objects and as an inner fields;
2. Copying of objects with circular references;
3. Copying of fields with any access modifier;
4. Copying of objects with class inheritance;
5. Copying of objects by interface link.