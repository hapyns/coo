package coo.core.report.ichart;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChartTest {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Test
	public void test() throws Exception {
		Chart chart = new Chart();
		log.debug(chart.getDatas());
		chart.addData("北京", new Integer[] { -9, 1, 12, 20, 26, 30, 32, 29, 22,
				12, 0, -6 }, "#1f7e92");
		log.debug(chart.getDatas());

		chart = new Chart();
		chart.addData("IE", 35.75, "#a5c2d5");
		chart.addData("Chrome", 29.84, "#cbab4f");
		chart.addData("Firefox", 24.88, "#76a871");
		chart.addData("Safari", 6.77, "#9f7961");
		chart.addData("Opera", 2.02, "#a56f8f");
		chart.addData("Other", 0.73, "#6f83a5");
		log.debug(chart.getDatas());

		chart = new Chart();
		chart.addLabel("北京", "上海", "广州", "深圳");
		chart.addData("一月", new Integer[] { 45, 52, 54, 60 }, "#4f81bd");
		chart.addData("二月", new Integer[] { 60, 80, 105, 80 }, "#bd4d4a");
		chart.addData("三月", new Integer[] { 50, 70, 120, 100 }, "#98c045");
		log.debug(chart.getDatas());
		log.debug(chart.getLabels());
	}
}