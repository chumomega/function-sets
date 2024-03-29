package funsets

import org.junit._

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite {

  import FunSets._

  @Test def `contains is implemented`: Unit = {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = (x: Int) => x > 3
    val s5 = (x: Int) => x > 5
  }

  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remvoe the
   * @Ignore annotation.
   */
//  @Ignore("not ready yet")
  @Test def `singleton set one contains one`: Unit = {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  @Test def `union contains all elements of each set`: Unit = {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  @Test def `intersection contains all elements in both sets`: Unit = {
    new TestSets {
      val s = intersect(s4, s5)
      assert(contains(s, 7), "intersect 7")
      assert(!contains(s, 2), "intersect 2 should be false")
    }
  }

  @Test def `diff contains all elements in set 1 and not in set 2`: Unit = {
    new TestSets {
      val s = diff(s4, s5)
      assert(contains(s, 4), "diff 7")
      assert(!contains(s, 7), "diff 7 should be false")
    }
  }

  @Test def `filter test`: Unit = {
    new TestSets {
      val func1: Int => Boolean = (x: Int) => x%2 == 0
      val filtered_set = filter(s4, func1)
      assert(contains(filtered_set, 8), "filter 8")
      assert(!contains(filtered_set, 7), "diff 7 should be false")
    }
  }

  @Test def `forAll test 1`: Unit = {
    new TestSets {
      val func1: Int => Boolean = (x: Int) => x>0
      val is_for_all: Boolean = forall(s5, func1)
      assert(is_for_all, "is for all greater than 0")
//      assert(!contains(filtered_set, 7), "diff 7 should be false")
    }
  }

  @Test def `forAll test 2`: Unit = {
    new TestSets {
      val func1: Int => Boolean = (x: Int) => x>10
      val is_for_all: Boolean = forall(s5, func1)
      assert(!is_for_all, "is not for all greater than 0")
//      assert(!contains(filtered_set, 7), "diff 7 should be false")
    }
  }

  @Test def `exists test 1`: Unit = {
    new TestSets {
      val func1: Int => Boolean = (x: Int) => x>30
      val exists_in: Boolean = exists(s5, func1)
      assert(exists_in, "bounded exists in range")
//      assert(!contains(filtered_set, 7), "diff 7 should be false")
    }
  }

  @Test def `exists test 2`: Unit = {
    new TestSets {
      val func1: Int => Boolean = (x: Int) => x>1000
      val exists_in: Boolean = exists(s5, func1)
      assert(!exists_in, "bounded does not exist in range")
    }
  }

  @Rule def individualTestTimeout = new org.junit.rules.Timeout(10 * 1000)
}
