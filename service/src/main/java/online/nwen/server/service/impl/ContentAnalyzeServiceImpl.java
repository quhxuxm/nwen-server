package online.nwen.server.service.impl;

import online.nwen.server.bo.ContentAnalyzeResultBo;
import online.nwen.server.service.api.IContentAnalyzeService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Base64;

@Service
class ContentAnalyzeServiceImpl implements IContentAnalyzeService {
    private static final Logger logger = LoggerFactory.getLogger(ContentAnalyzeServiceImpl.class);
    private static final String IMG_TAG = "img";
    private static final String VIDEO_TAG = "video";
    private static final String AUDIO_TAG = "audio";

    @Override
    public ContentAnalyzeResultBo analyze(String content) {
        ContentAnalyzeResultBo result = new ContentAnalyzeResultBo();
        Document cleanDocument = Jsoup.parse(Jsoup.clean(content, this.createWhiteList()));
        this.parseMediaContent(cleanDocument, IMG_TAG, result);
        this.parseMediaContent(cleanDocument, VIDEO_TAG, result);
        this.parseMediaContent(cleanDocument, AUDIO_TAG, result);
        String contentHtml = cleanDocument.body().html();
        result.setContent(contentHtml);
        return result;
    }

    private Whitelist createWhiteList() {
        return Whitelist.none()
                .addTags("audio", "video", "img", "div", "p", "h1", "h2", "h3", "h4", "b", "i", "blockquote", "br")
                .addAttributes("img", "src")
                .addAttributes("video", "src")
                .addAttributes("audio", "src")
                .addProtocols("img", "src", "data")
                .addProtocols("audio", "src", "data")
                .addProtocols("video", "src", "data");
    }

    private void parseMediaContent(Document document, String tagName,
                                   ContentAnalyzeResultBo articleContentAnalyzeResponse) {
        Elements imgElements = document.getElementsByTag(tagName);
        imgElements.forEach(element -> {
            String src = element.attr("src");
            if (src == null) {
                element.remove();
                logger.debug(
                        "Ignore the media content because it dose not contains a src value");
                return;
            }
            if (!src.startsWith("data:")) {
                logger.debug(
                        "Skip the image because it is not a base64 content.");
                return;
            }
            String[] srcParts = src.split(",");
            if (srcParts.length < 2) {
                element.remove();
                logger.debug(
                        "Ignore the image because it is not a valid base64 content: src did not contains media type part and content part.");
                return;
            }
            String typePart = srcParts[0];
            if (!typePart.contains(";")) {
                logger.debug(
                        "Ignore the image because it is not a valid base64 content: the media type part is invalid.");
                return;
            }
            int mediaTypeEndIndex = typePart.indexOf(";");
            String mediaType = typePart.substring("data:".length(), mediaTypeEndIndex);
            String base64Content = srcParts[1];
            byte[] mediaByteContent = Base64.getDecoder()
                    .decode(base64Content);
            String mediaMd5 = DigestUtils.md5DigestAsHex(mediaByteContent);
            ContentAnalyzeResultBo.MediaContent mediaContent = new ContentAnalyzeResultBo.MediaContent();
            mediaContent.setMd5(mediaMd5);
            mediaContent.setContentType(mediaType);
            mediaContent.setContent(mediaByteContent);
            articleContentAnalyzeResponse.getMediaContents().put(mediaMd5, mediaContent);
            element.attr("src", "/api/resource/" + mediaMd5);
        });
    }
}
