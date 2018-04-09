package com.test.apnacomplex.modules;

import com.test.apnacomplex.R;
import com.test.apnacomplex.modules.dto.HomeGridItemDto;
import com.test.apnacomplex.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sc on 9/4/18.
 */

public class DataHelper {

    /**
     * Provides data for Home screen Grid
     * @return
     */
    public static List<HomeGridItemDto> getGridList() {
        List<HomeGridItemDto> list = new ArrayList<>();
        list.add(new HomeGridItemDto(Constants.HomeGridItemIds.REPOSITORY, R.drawable.repository_bg, "REPOSITORY"));
        list.add(new HomeGridItemDto(Constants.HomeGridItemIds.LOREM_IPSUM_1, R.drawable.dummy1_bg, "LOREM IPSUM"));
        list.add(new HomeGridItemDto(Constants.HomeGridItemIds.LOREM_IPSUM_2, R.drawable.dummy2_bg, "LOREM IPSUM"));
        list.add(new HomeGridItemDto(Constants.HomeGridItemIds.LOREM_IPSUM_3, R.drawable.dummy_3bg, "LOREM IPSUM"));
        list.add(new HomeGridItemDto(Constants.HomeGridItemIds.LOREM_IPSUM_4, R.drawable.dummy4_bg, "LOREM IPSUM"));
        return list;
    }
}
