/*
 * Copyright (c) 2014 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.vo;

import java.util.List;

/**
 * Openworks4에서 채택한 Tree UI 스크립트 트리(Tree) 구조 VO
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 4. 20.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class TreeVO extends BaseVO {

    /** serialVersionUID */
    private static final long serialVersionUID = -1787543102831933940L;

    /** 기본키(key 이외의 추가 기본 조건 키. 필요시사용) */
    private String baseKey;

    /** 최상위코드를 사용하는 경우 */
    private String topKey;

    /** 키에 해당하는 문자열 */
    private String key;

    /** 트리에 표시될 명칭 */
    private String title;

    /** 펼침여부 */
    private Boolean expanded;

    /** 사용여부 */
    private String useYn;

    /** 선택여부 */
    private String checkAt;

    /** 선택여부(json으로 변환시) */
    private Boolean selected;

    /** 선택불가여부 */
    private Boolean unselectable;

    /** 값(필요 시 사용) */
    private String value;

    /** URL 데이터 전달 */
    private String href;

    /** 폴더표시 여부 */
    private Boolean folder;

    /** ajax로 데이터를 로드할지 여부 */
    private Boolean lazy;

    /** 자식모드 목록 */
    private List<TreeVO> children;

    /**
     * String baseKey을 반환
     * 
     * @return String baseKey
     */
    public String getBaseKey() {
        return baseKey;
    }

    /**
     * baseKey을 설정
     * 
     * @param baseKey 을(를) String baseKey로 설정
     */
    public void setBaseKey(String baseKey) {
        this.baseKey = baseKey;
    }

    /**
     * String topKey을 반환
     * 
     * @return String topKey
     */
    public String getTopKey() {
        return topKey;
    }

    /**
     * topKey을 설정
     * 
     * @param topKey 을(를) String topKey로 설정
     */
    public void setTopKey(String topKey) {
        this.topKey = topKey;
    }

    /**
     * String key을 반환
     * 
     * @return String key
     */
    public String getKey() {
        return key;
    }

    /**
     * key을 설정
     * 
     * @param key 을(를) String key로 설정
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * String title을 반환
     * 
     * @return String title
     */
    public String getTitle() {
        return title;
    }

    /**
     * title을 설정
     * 
     * @param title 을(를) String title로 설정
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Boolean expanded을 반환
     * 
     * @return Boolean expanded
     */
    public Boolean getExpanded() {
        return expanded;
    }

    /**
     * expanded을 설정
     * 
     * @param expanded 을(를) Boolean expanded로 설정
     */
    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    /**
     * String useYn을 반환
     * 
     * @return String useYn
     */
    public String getUseYn() {
        return useYn;
    }

    /**
     * useYn을 설정
     * 
     * @param useYn 을(를) String useYn로 설정
     */
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    /**
     * String checkAt을 반환
     * 
     * @return String checkAt
     */
    public String getCheckAt() {
        return checkAt;
    }

    /**
     * checkAt을 설정
     * 
     * @param checkAt 을(를) String checkAt로 설정
     */
    public void setCheckAt(String checkAt) {
        this.checkAt = checkAt;
    }

    /**
     * Boolean selected을 반환
     * 
     * @return Boolean selected
     */
    public Boolean getSelected() {
        return selected;
    }

    /**
     * selected을 설정
     * 
     * @param selected 을(를) Boolean selected로 설정
     */
    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    /**
     * Boolean unselectable을 반환
     * 
     * @return Boolean unselectable
     */
    public Boolean getUnselectable() {
        return unselectable;
    }

    /**
     * unselectable을 설정
     * 
     * @param unselectable 을(를) Boolean unselectable로 설정
     */
    public void setUnselectable(Boolean unselectable) {
        this.unselectable = unselectable;
    }

    /**
     * String value을 반환
     * 
     * @return String value
     */
    public String getValue() {
        return value;
    }

    /**
     * value을 설정
     * 
     * @param value 을(를) String value로 설정
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * String href을 반환
     * 
     * @return String href
     */
    public String getHref() {
        return href;
    }

    /**
     * href을 설정
     * 
     * @param href 을(를) String href로 설정
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * Boolean folder을 반환
     * 
     * @return Boolean folder
     */
    public Boolean getFolder() {
        return folder;
    }

    /**
     * folder을 설정
     * 
     * @param folder 을(를) Boolean folder로 설정
     */
    public void setFolder(Boolean folder) {
        this.folder = folder;
    }

    /**
     * Boolean lazy을 반환
     * 
     * @return Boolean lazy
     */
    public Boolean getLazy() {
        return lazy;
    }

    /**
     * lazy을 설정
     * 
     * @param lazy 을(를) Boolean lazy로 설정
     */
    public void setLazy(Boolean lazy) {
        this.lazy = lazy;
    }

    /**
     * List<TreeVO> children을 반환
     * 
     * @return List<TreeVO> children
     */
    public List<TreeVO> getChildren() {
        return children;
    }

    /**
     * children을 설정
     * 
     * @param children 을(를) List<TreeVO> children로 설정
     */
    public void setChildren(List<TreeVO> children) {
        this.children = children;
    }

}
