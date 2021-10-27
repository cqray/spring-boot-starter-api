package cn.cqray.springboot.filter;

import cn.cqray.springboot.ApiConfiguration;
import cn.cqray.springboot.ApiConstants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;

/**
 * 请求修饰过滤器，使ServletRequest可以反复读取
 * @author Cqray
 */
@Slf4j
@Order(Integer.MIN_VALUE)
@Component(value = ApiConstants.FILTER_REQUEST)
@WebFilter(urlPatterns = "/**", filterName = ApiConstants.FILTER_REQUEST)
public class RequestFilter implements Filter {

    private final ApiConfiguration hotchpotchConfiguration;

    public RequestFilter(ApiConfiguration hotchpotchConfiguration) {
        this.hotchpotchConfiguration = hotchpotchConfiguration;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.info(getClassName() + "初始化成功");
    }

    @Override
    public void doFilter(@NotNull final ServletRequest request,
                         @NotNull final ServletResponse response,
                         @NotNull final FilterChain chain) throws IOException, ServletException {
        boolean useRequestFilter = hotchpotchConfiguration.getApiConfig().isUseRequestFilter();
        if (useRequestFilter) {
            // 防止流读取一次就失效
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            ServletRequest requestWrapper = new HttpServletRequestWrapper2(httpServletRequest);
            chain.doFilter(requestWrapper, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {}

    private static String getClassName() {
        return String.format("[请求全局过滤器][%s]", RequestFilter.class.getSimpleName());
    }

    /**
     * 可反复读取的ServletRequest
     */
    private static class HttpServletRequestWrapper2 extends HttpServletRequestWrapper {

        private final byte[] body;

        @SneakyThrows
        public HttpServletRequestWrapper2(HttpServletRequest request) {
            super(request);
            String bodyString = getBodyString(request);
            body = bodyString.getBytes(getCharsetName(request));
        }

        @Override
        public BufferedReader getReader() {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }

        @Override
        public ServletInputStream getInputStream() {
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
            return new ServletInputStream() {
                @Override
                public int read() {
                    return inputStream.read();
                }

                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {
                }
            };
        }

        @NotNull
        private String getBodyString(@NotNull final ServletRequest request) {
            StringBuilder sb = new StringBuilder();
            InputStream inputStream = null;
            BufferedReader reader = null;
            try {
                inputStream = cloneInputStream(request.getInputStream());
                reader = new BufferedReader(new InputStreamReader(inputStream, getCharsetName(request)));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException exc) {
                log.error(getClassName() + "获取请求Body异常", exc);
            } finally {
                closeStream(inputStream);
                closeStream(reader);
            }
            return sb.toString();
        }

        @NotNull
        private InputStream cloneInputStream(@NotNull ServletInputStream inputStream) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            try {
                while ((len = inputStream.read(buffer)) > -1) {
                    outputStream.write(buffer, 0, len);
                }
                outputStream.flush();
            } catch (IOException exc) {
                log.error(getClassName() + "复制请求输入流异常", exc);
            }
            return new ByteArrayInputStream(outputStream.toByteArray());
        }

        private void closeStream(Closeable closeable) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException exc) {
                    log.error(getClassName() + "流关闭异常", exc);
                }
            }
        }

        @NotNull
        private String getCharsetName(@NotNull final ServletRequest request) {
            String charsetName = request.getCharacterEncoding();
            return charsetName == null ? Charset.defaultCharset().name() : charsetName;
        }
    }
}
