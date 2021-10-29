package cn.cqray.springboot.api.response;

import java.util.Collection;
import java.util.List;

public interface ResponseDataPageProvider {

    Object getPage(List<?> data);
}
