package zesinc.web.spring.interceptor;

import javax.servlet.http.HttpServletRequest;

import zesinc.web.utils.RequestPathMatcher.PathVO;

/**
 * HttpServletRequest 객체를 이용하여 적용/미적용 여부를 판단하여 필터링 하기 위한 규칙
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 * 2012. 2. 26.    방기배   신규 등록
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public interface IBaseInterceptor {

    /**
     * 대상 URL 패턴과 일치 하는지 여부를 확인
     * 
     * @param request
     * @return PathVO 패턴과 일치시 일치된 매핑객체를 반환
     */
    PathVO validate(HttpServletRequest request);

    /**
     * 경로 일치 여부만 확인하기에는 모든 URL에 대하여 대상이 되어
     * 불필요한 자원 낭비가 심하다. 따라서 경로 매핑 전에 컨트롤러를 우선 확인하는
     * 절차를 거쳐 우선 필터링을 하도록 한다.
     * 
     * @param handler
     * @return
     */
    boolean supported(Object handler);
}
