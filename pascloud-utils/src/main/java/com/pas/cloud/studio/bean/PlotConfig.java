package com.pas.cloud.studio.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 *说明：图表配置信息类<br>
 *
 *@author: Alan 创建时间：Sep 3, 20129:46:40 AM
 */
public class PlotConfig  implements Serializable{
	/** */
	private static final long serialVersionUID = 1L;
	private int needPlot;
	/** 图表ID 如果有多个数据源时，做区分*/
	private String id;
	/**
	 * 图表自定义处理函数
	 */
	private String custPlotMth;
	/** 图表类型：plot-bar、plot-line、plot-pie*/
	private String plotType;
	/** 图表标题*/
	private String plotTitle;
	/** x轴刻度*/
	private String xSeries;
	/** y轴刻度*/
	private List<String> ySeries;
	/** 条形图说明*/
	private List<String> ySeriesDesc;
	/** x轴描述*/
	private String xLabel;
	/** y轴描述*/
	private String yLabel;
	/** 饼图描述字段*/
	private String pieDesc;
	/** 饼图取值字段*/
	private String pieValue;
	/** 仪表盘取值字段*/
	private String gaugeValue;
	/** 仪表盘取值范围*/
	//private String gaugeScope;
	/** 仪表盘最小值*/
	private String minValue;
	/** 仪表盘最大值*/
	private String maxValue;
	/** 仪表盘颜色区间*/
	private String intervals;
	/** 仪表盘刻度值*/
	private String gaugeScale;
	/** 仪表盘单位*/
	private String gaugeMeasure;
	/** 仪表盘颜色*/
	private String gaugeColors;
	/** 仪表盘颜色间隔*/
	//private String gaugeIntervals;
	
	public String getPlotType() {
		return plotType;
	}
	public void setPlotType(String plotType) {
		this.plotType = plotType;
	}
	
	public String getPlotTitle() {
		return plotTitle;
	}
	public void setPlotTitle(String plotTitle) {
		this.plotTitle = plotTitle;
	}
	
	public String getXSeries() {
		return xSeries;
	}
	public void setXSeries(String series) {
		xSeries = series;
	}
	public List<String> getYSeries() {
		return ySeries;
	}
	public void setYSeries(List<String> series) {
		ySeries = series;
	}
	public String getXLabel() {
		return xLabel;
	}
	public void setXLabel(String label) {
		xLabel = label;
	}
	public String getYLabel() {
		return yLabel;
	}
	public void setYLabel(String label) {
		yLabel = label;
	}
	public String getPieDesc() {
		return pieDesc;
	}
	public void setPieDesc(String pieDesc) {
		this.pieDesc = pieDesc;
	}
	public String getPieValue() {
		return pieValue;
	}
	public void setPieValue(String pieValue) {
		this.pieValue = pieValue;
	}
	public String getGaugeValue() {
		return gaugeValue;
	}
	public void setGaugeValue(String gaugeValue) {
		this.gaugeValue = gaugeValue;
	}
	/*public String getGaugeScope() {
		return gaugeScope;
	}
	public void setGaugeScope(String gaugeScope) {
		this.gaugeScope = gaugeScope;
	}*/
	
	public String getGaugeScale() {
		return gaugeScale;
	}
	public void setGaugeScale(String gaugeScale) {
		this.gaugeScale = gaugeScale;
	}
	public String getGaugeMeasure() {
		return gaugeMeasure;
	}
	public void setGaugeMeasure(String gaugeMeasure) {
		this.gaugeMeasure = gaugeMeasure;
	}
	public String getGaugeColors() {
		return gaugeColors;
	}
	public void setGaugeColors(String gaugeColors) {
		this.gaugeColors = gaugeColors;
	}
	/*public String getGaugeIntervals() {
		return gaugeIntervals;
	}
	public void setGaugeIntervals(String gaugeIntervals) {
		this.gaugeIntervals = gaugeIntervals;
	}*/

	public int getNeedPlot() {
		return needPlot;
	}
	public void setNeedPlot(int needPlot) {
		this.needPlot = needPlot;
	}
	public List<String> getYSeriesDesc() {
		return ySeriesDesc;
	}
	public void setYSeriesDesc(List<String> seriesDesc) {
		ySeriesDesc = seriesDesc;
	}
	public String getMinValue() {
		return minValue;
	}
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	public String getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	public String getIntervals() {
		return intervals;
	}
	public void setIntervals(String intervals) {
		this.intervals = intervals;
	}
	public String getCustPlotMth() {
		return custPlotMth;
	}
	public void setCustPlotMth(String custPlotMth) {
		this.custPlotMth = custPlotMth;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
