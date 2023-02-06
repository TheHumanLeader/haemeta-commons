package com.haemeta.common.service;

import com.haemeta.common.entity.TestEntityVo;
import com.haemeta.common.entity.TestInfoEntity;
import com.haemeta.common.interfaces.HaeBasicService;
import com.haemeta.common.utils.lang.ListUtil;
import com.haemeta.common.utils.lang.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestService implements HaeBasicService {

    public List<TestInfoEntity> selectInfo(Integer... code) {
        List<TestInfoEntity> res = new ArrayList<>();
        ListUtil.asList(code).forEach(c -> res.add(new TestInfoEntity(c, c + "-info-" + StringUtil.randomString_09azAz(5))));
        return res;
    }

    public List<TestEntityVo> selectEntity(Integer... code) {
        List<TestEntityVo> res = Arrays.asList(code).stream().map(i -> new TestEntityVo(i + "-name-" + StringUtil.randomString_09azAz(5), i)).collect(Collectors.toList());
        return res;
    }

}
