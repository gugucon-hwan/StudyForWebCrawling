package Test01;

import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

public class Ex01 {
	static String BASE_URL_F = "https://search.naver.com/search.naver?where=news&sm=tab_jum&query=%EB%B9%85%EB%8D%B0%EC%9D%B4%ED%84%B0";// 페이지가
	// 나오기전  "="까지 복사해서 붙여 넣음
	static String BASE_URL_B = "&refresh_start=0";// "="뒤에 숫자를 제외한 나머지 부분을 붙여 넣음

	static int BASE_URL_PAGE = 1;// 페이지를 변경하기 위한 변수
	static String COMPLETE_URL = BASE_URL_F + BASE_URL_PAGE + BASE_URL_B;// 완성된 URL주소

	public static void main(String[] args) throws IOException {
		int page = 1;	//크롤링을 시작하는 페이지의 시작페이지를 입력한다.
		while (page <= 101) { //크롤링하고 싶은 페이지의 끝페이지를 입력한다.
			Document naver_news = Jsoup.connect(COMPLETE_URL)
					.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; Trident/7.0; rv:11.0) like Gecko")
					.header("Connection", "Keep-Alive").get();
			Elements news_url = naver_news.select("a._sp_each_url");
			for (Element element : news_url) {
				// for문으로 뒤에 변수가 끝날 때까지 앞에 있는 변수에 대입
				String url = element.attr("href");
				// element에서 attr함수를 사용해 원하는 부분만 가져옴

				if (url.contains("news.naver.com")) {
					System.out.println(url);
					Document doc = Jsoup.connect(url).get();

					// Document
					// doc=Jsoup.connect("https://news.naver.com/main/read.nhn?mode=LSD&mid=shm&sid1=105&oid=293&aid=0000030463").get();
					Elements body = doc.select("div#articleBodyContents");// 본문 크롤링
					Elements title = doc.select("h3#articleTitle");// 제목 크롤링
					String str = body.text();
					System.out.println("본문 : " + str);
					System.out.println("제목 : " + title.text());// 글 정제하는 다른 방법
				}//if
			}//for
			System.out.println("페이지 : " + page + "========================================");
			//"===="을 입력하여 한 페이지씩 구분해줌
			page = page + 10; //네이버 뉴스는 페이지가 10씩 올라가므로 10을 더해줌
			BASE_URL_PAGE = page;
			COMPLETE_URL = BASE_URL_F + BASE_URL_PAGE + BASE_URL_B;
			//변경된 페이지 변수를 URL에 적용하여 저장한다.
		}//while
	}//main

}
