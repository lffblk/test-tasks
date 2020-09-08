package com.lffblk.clone;

import com.lffblk.clone.model.ComplexObject;
import com.lffblk.clone.model.ObjectInterface;
import com.lffblk.clone.model.ObjectWithPrimitivesInConstructor;
import com.lffblk.clone.model.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CopyUtilsTest {

    private Person jon;
    private Person robb;
    private Person bran;
    private Person rickon;
    private Person sansa;
    private Person arya;

    @Before
    public void init() {
        jon = new Person("Jon Snow", 24, Collections.singletonList("A Game of Thrones"));
        robb = new Person("Robb Stark", 22, Arrays.asList("A Clash of Kings", "The Winds of Winter"));
        bran = new Person("Bran Stark", 8, Collections.singletonList("A Dance with Dragons"));
        rickon = new Person("Rickon Stark", 7, Arrays.asList("A Game of Thrones", "A Storm of Swords"));
        sansa = new Person("Sansa Stark", 18, Arrays.asList("A Clash of Kings", "A Storm of Swords"));
        arya = new Person("Arya Stark", 16, Collections.singletonList("A Dance with Dragons"));
    }

    @Test
    public void deepCopy_primitive() {
        int primitive = 42;
        int copy = CopyUtils.deepCopy(primitive);
        assertEquals(primitive, copy);
    }

    @Test
    public void deepCopy_arrayOfPrimitives() {
        int[] primitives = {4, 8, 15, 16, 23, 42};
        int[] copy = CopyUtils.deepCopy(primitives);
        ComplexObjectsComparator.assertDeepCopy(primitives, copy);
    }

    @Test
    public void deepCopy_string() {
        String str = "s";
        String copy = CopyUtils.deepCopy(str);
        ComplexObjectsComparator.assertDeepCopy(str, copy);
    }

    @Test
    public void deepCopy_enum() {
        PrimitiveType enumValue = PrimitiveType.SHORT;
        PrimitiveType copy = CopyUtils.deepCopy(enumValue);
        ComplexObjectsComparator.assertDeepCopy(enumValue, copy);
    }

    @Test
    public void deepCopy_person() {
        Person copy = CopyUtils.deepCopy(jon);
        ComplexObjectsComparator.assertDeepCopy(jon, copy);
    }

    @Test
    public void deepCopy_arrayOfPersons() {
        Person[] nedStarkDaughters = {sansa, arya};
        Person[] copy = CopyUtils.deepCopy(nedStarkDaughters);
        ComplexObjectsComparator.assertDeepCopy(nedStarkDaughters, copy);
    }

    @Test
    public void deepCopy_listOfPersons() {
        List<Person> nedStarkSons = new ArrayList<>(Arrays.asList(jon, robb, bran, rickon));
        List<Person> copy = CopyUtils.deepCopy(nedStarkSons);
        ComplexObjectsComparator.assertDeepCopy(nedStarkSons, copy);
    }

    @Test
    public void deepCopy_mapOfPersons() {
        Map<String, Person> nedStarkDaughters = new HashMap<>();
        nedStarkDaughters.put(sansa.getName(), sansa);
        nedStarkDaughters.put(arya.getName(), arya);
        Map<String, Person> copy = CopyUtils.deepCopy(nedStarkDaughters);
        ComplexObjectsComparator.assertDeepCopy(nedStarkDaughters, copy);
    }

    @Test
    public void deepCopy_setOfPersons() {
        Set<Person> nedStarkDaughters = new HashSet<>(Arrays.asList(sansa, arya));
        Set<Person> copy = CopyUtils.deepCopy(nedStarkDaughters);
        assertEquals(nedStarkDaughters, copy);
        ComplexObjectsComparator.assertDeepCopy(nedStarkDaughters, copy);
    }

    @Test
    public void deepCopy_queueOfPersons() {
        Queue<Person> nedStarkDaughters = new LinkedList<>();
        nedStarkDaughters.offer(sansa);
        nedStarkDaughters.offer(arya);
        Queue<Person> copy = CopyUtils.deepCopy(nedStarkDaughters);
        ComplexObjectsComparator.assertDeepCopy(nedStarkDaughters, copy);
    }

    @Test
    public void deepCopy_complexObject() {
        Person[] nedStarkSons = {jon, robb, bran, rickon};
        Person[] nedStarkDaughters = {sansa, arya};
        Person[][] linkedListElements = {nedStarkSons, nedStarkDaughters};
        ComplexObject complexObject = new ComplexObject(linkedListElements, PrimitiveType.BOOLEAN);
        ComplexObject copy = CopyUtils.deepCopy(complexObject);
        ComplexObjectsComparator.assertDeepCopy(complexObject, copy);
    }

    @Test
    public void deepCopy_complexObject_byInterface() {
        Person[] nedStarkDaughters = {sansa, arya};
        ObjectInterface complexObject = new ComplexObject(nedStarkDaughters, null);
        ObjectInterface copy = CopyUtils.deepCopy(complexObject);
        ComplexObjectsComparator.assertDeepCopy(complexObject, copy);
    }

    @Test
    public void deepCopy_objectWithPrimitivesInConstructor() {
        ObjectWithPrimitivesInConstructor object = new ObjectWithPrimitivesInConstructor((byte) 1, (short) 1, 1,
            1, 1.0f, 1.0, '1', true);
        ObjectWithPrimitivesInConstructor copy = CopyUtils.deepCopy(object);
        ComplexObjectsComparator.assertDeepCopy(object, copy);
    }
}