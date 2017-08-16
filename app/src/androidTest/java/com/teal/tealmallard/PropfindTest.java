package com.teal.tealmallard;

import com.teal.mallard.Mallard;
import com.teal.mallard.controller.Quacker;
import com.teal.mallard.model.Propfind;
import com.teal.mallard.model.PropfindIncubator;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by kodyvanry on 8/7/17.
 */

public class PropfindTest {

    private Mallard mallard;

    @Before
    public void before() {
        mallard = Mallard.getInstance();
        mallard.setUrl("http://10.73.41.178");
    }

    @Test
    public void propfind() {
        Quacker quacker = new Quacker();
        quacker.quack(new PropfindIncubator(mallard, "flight").hatch());
    }
}
