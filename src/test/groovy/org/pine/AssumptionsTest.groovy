package org.pine

import org.junit.Before
import org.junit.Test
import org.pine.annotation.Describe
import org.pine.behavior.Behavior

import static org.pine.testHelpers.TestHelper.assertBehaviorPasses

class AssumptionsTest {

    SpecRunner runner

    class AssumptionsSpec implements Spec {
        int someNumber = 0
        int anotherNumber = 1

        @Describe("AssumptionsSpec")
        def spec() {
            assume {
                someNumber = 1
            }

            assume {
                anotherNumber = anotherNumber + 1
            }

            when 'someNumber is updated', {
                assume { someNumber = 4 }

                it 'finds the updated number', {
                    assert someNumber == 4
                }
            }

            it 'asserts about an assumption', {
                assert someNumber == 1

                anotherNumber = 7
                assert anotherNumber == 7
            }

            it 'asserts about another number', {
                assert someNumber == 1
                assert anotherNumber == 2
            }
        }
    }

    @Before
    void setUp() {
        runner = new SpecRunner(AssumptionsSpec)
    }

    @Test
    public void itMakesAssumptionsUsedInTests () {
        assertBehaviorPasses(runner, runner.getChildren().get(0))
    }

    @Test
    public void itResetsAssumptionsForEachTest () {
        List<Behavior> behaviors = runner.getChildren()

        assert behaviors.size == 3
        behaviors.each{ b -> assertBehaviorPasses(runner, b) }
    }

}
