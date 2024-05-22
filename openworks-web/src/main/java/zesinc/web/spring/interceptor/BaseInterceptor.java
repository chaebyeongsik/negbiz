package zesinc.web.spring.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.HandlerInterceptor;

import zesinc.core.lang.Validate;
import zesinc.web.utils.RequestPathMatcher;
import zesinc.web.utils.RequestPathMatcher.PathVO;

/**
 * Interceptor에 URL 패턴을 사용하여 적용/미적용 대상 필터링을 기본 제공하기 위한 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 2. 26.    방기배   신규 등록
 *  2015. 7. 30.    방기배   matcher 변경
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public abstract class BaseInterceptor implements HandlerInterceptor, IBaseInterceptor {

    private RequestPathMatcher matcher = new RequestPathMatcher();

    /**
     * 대상 URL 패턴 설정
     * 
     * @param urls
     */
    public void setInclude(String[] urls) {
        if(Validate.isNotEmpty(urls)) {
            matcher.setInclude(urls);
        }
    }

    /**
     * 제외 URL 패턴 설정
     * 
     * @param urls
     */
    public void setExclude(String[] urls) {
        if(Validate.isNotEmpty(urls)) {
            matcher.setExclude(urls);
        }
    }

    @Override
    public PathVO validate(HttpServletRequest request) {

        return matcher.match(request);
    }

}
