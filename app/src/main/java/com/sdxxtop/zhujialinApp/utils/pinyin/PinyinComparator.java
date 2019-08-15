package com.sdxxtop.zhujialinApp.utils.pinyin;


import com.sdxxtop.zhujialinApp.data.ContactIndexBean;

import java.util.Comparator;

/**
 * @author xiaanming
 */
public class PinyinComparator implements Comparator<ContactIndexBean.ContactBean> {

    @Override
    public int compare(ContactIndexBean.ContactBean o1, ContactIndexBean.ContactBean o2) {
        if (o1 == null || o2 == null) {
            return -1;
        }
        if ("@".equals(o1.sortLetters)
                || "#".equals(o2.sortLetters)) {
            return -1;
        } else if ("#".equals(o1.sortLetters)
                || "@".equals(o2.sortLetters)) {
            return 1;
        } else {
            return o1.sortLetters.compareTo(o2.sortLetters);
        }
    }

}
