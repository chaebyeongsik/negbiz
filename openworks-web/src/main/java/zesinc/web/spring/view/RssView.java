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

import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import zesinc.core.lang.Validate;
import zesinc.web.support.BaseConfig;
import zesinc.web.utils.MessageUtil;
import zesinc.web.vo.FeedVO;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Content;
import com.rometools.rome.feed.rss.Item;

/**
 * RSS, Atom Feed 를 제공한다.
 *
 * <pre>
 *
 * << 개정이력(Modification Information) >>
 *
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 3. 4.    방기배   신규 등록
 * </pre>
 *
 * @author (주)제스아이엔씨 기술연구소
 * @see AbstractRssFeedView
 */
public class RssView extends AbstractRssFeedView {

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Channel feed, HttpServletRequest request) {
        FeedVO feedVo = (FeedVO) model.get(BaseConfig.RSS_DATA_KEY);

        if(Validate.isEmpty(feedVo.getTitle())) {
            feed.setTitle(MessageUtil.getMessage("common.invalidParam"));
            feed.setLink(MessageUtil.getMessage("common.invalidParam"));
            feed.setDescription(MessageUtil.getMessage("common.invalidParam"));
        } else {
            feed.setTitle(feedVo.getTitle());
            feed.setLink(feedVo.getLink());
            feed.setDescription(feedVo.getDescription());
        }
    }

    @Override
    protected List<Item> buildFeedItems(Map<String, Object> model, HttpServletRequest request,
        HttpServletResponse response) throws Exception {

        List<Item> feedItems = new ArrayList<Item>();

        FeedVO baseFeedVo = (FeedVO) model.get(BaseConfig.RSS_DATA_KEY);
        List<FeedVO> itemList = baseFeedVo.getItemList();
        if(Validate.isNotNull(itemList)) {
            for(FeedVO feedVo : itemList) {
                Content content = new Content();
                content.setType(Content.HTML);
                content.setValue(feedVo.getDescription());

                Item item = new Item();
                item.setTitle(feedVo.getTitle());
                item.setAuthor(feedVo.getAuthor());
                item.setLink(feedVo.getLink());
                item.setPubDate(feedVo.getPubDate());
                item.setContent(content);

                feedItems.add(item);
            }
        }

        return feedItems;
    }
}
