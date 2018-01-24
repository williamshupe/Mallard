package com.tealdrones.tealmallard;

import com.tealdrones.mallard.Mallard;
import com.tealdrones.mallard.controller.Quacker;
import com.tealdrones.mallard.model.Propfind;
import com.tealdrones.mallard.model.PropfindIncubator;

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
        mallard.setUrl("http://10.73.41.98");
    }

    @Test
    public void propfind() {
        Quacker quacker = new Quacker();
        quacker.quack(new PropfindIncubator(mallard, "flight").hatch());
    }
}
