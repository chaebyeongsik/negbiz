/*
 * Copyright (c) 2014 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.spring.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.feed.AbstractAtomFeedView;

import zesinc.core.lang.Validate;
import zesinc.web.support.BaseConfig;
import zesinc.web.utils.MessageUtil;
import zesinc.web.vo.FeedVO;

import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.atom.Content;

/**
 * AtomFeed View 구현체
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 3. 4.    방기배   신규 등록
 * </pre>
 *
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class AtomView extends AbstractAtomFeedView {

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Feed feed, HttpServletRequest request) {
        FeedVO feedVo = (FeedVO) model.get(BaseConfig.ATOM_DATA_KEY);

        if(Validate.isEmpty(feedVo.getTitle())) {
            feed.setTitle(MessageUtil.getMessage("common.invalidParam"));
            feed.setId(MessageUtil.getMessage("common.invalidParam"));
        } else {
            feed.setTitle(feedVo.getTitle());
            feed.setId(feedVo.getLink());
        }
    }

    @Override
    protected List<Entry> buildFeedEntries(Map<String, Object> model, HttpServletRequest request,
        HttpServletResponse response) throws Exception {

        List<Entry> feedItems = new ArrayList<Entry>();

        FeedVO baseFeedVo = (FeedVO) model.get(BaseConfig.ATOM_DATA_KEY);
        List<FeedVO> itemList = baseFeedVo.getItemList();
        if(Validate.isNotNull(itemList)) {
            for(FeedVO feedVo : itemList) {
                Content summary = new Content();
                summary.setValue(feedVo.getDescription());

                Entry entry = new Entry();
                entry.setId(feedVo.getLink());
                entry.setTitle(feedVo.getTitle());
                entry.setSummary(summary);
                entry.setUpdated(feedVo.getPubDate());

                feedItems.add(entry);
            }
        }

        return feedItems;
    }
}
