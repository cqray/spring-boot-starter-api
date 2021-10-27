package cn.cqray.springboot.response;

import java.util.Collection;

public interface ResponseDataPageProvider {

    Object getPage(Collection<?> data);
}
