package com.pascloud.vo.mversion;

import java.io.Serializable;

public class XtcdVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer xmdh;
	
	private String  xmmc;
	
	private String  xmdz;
	
	private Integer sjxm;
	
	private String  cdjb;
	
	private String  dzlx;
	
	private String  classid;
	
	private Integer sfxs;
	
	private String  imgurl;
	
	private String  ckyxj;
	
	private Integer qxbs;
	
	private String  version;

	public Integer getXmdh() {
		return xmdh;
	}

	public void setXmdh(Integer xmdh) {
		this.xmdh = xmdh;
	}

	public String getXmmc() {
		return xmmc;
	}

	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}

	public String getXmdz() {
		return xmdz;
	}

	public void setXmdz(String xmdz) {
		this.xmdz = xmdz;
	}

	public Integer getSjxm() {
		return sjxm;
	}

	public void setSjxm(Integer sjxm) {
		this.sjxm = sjxm;
	}

	public String getCdjb() {
		return cdjb;
	}

	public void setCdjb(String cdjb) {
		this.cdjb = cdjb;
	}

	public String getDzlx() {
		return dzlx;
	}

	public void setDzlx(String dzlx) {
		this.dzlx = dzlx;
	}

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public Integer getSfxs() {
		return sfxs;
	}

	public void setSfxs(Integer sfxs) {
		this.sfxs = sfxs;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getCkyxj() {
		return ckyxj;
	}

	public void setCkyxj(String ckyxj) {
		this.ckyxj = ckyxj;
	}

	public Integer getQxbs() {
		return qxbs;
	}

	public void setQxbs(Integer qxbs) {
		this.qxbs = qxbs;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sjxm == null) ? 0 : sjxm.hashCode());
		result = prime * result + ((xmdh == null) ? 0 : xmdh.hashCode());
		result = prime * result + ((xmdz == null) ? 0 : xmdz.hashCode());
		result = prime * result + ((xmmc == null) ? 0 : xmmc.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XtcdVo other = (XtcdVo) obj;
		if (sjxm == null) {
			if (other.sjxm != null)
				return false;
		} else if (!sjxm.equals(other.sjxm))
			return false;
		if (xmdh == null) {
			if (other.xmdh != null)
				return false;
		} else if (!xmdh.equals(other.xmdh))
			return false;
		if (xmdz == null) {
			if (other.xmdz != null)
				return false;
		} else if (!xmdz.equals(other.xmdz))
			return false;
		if (xmmc == null) {
			if (other.xmmc != null)
				return false;
		} else if (!xmmc.equals(other.xmmc))
			return false;
		return true;
	}

	/*
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int result = 17;
		result = 31 * result + xmdh;
        result = 31 * result + xmdz.hashCode();
        result = 31 * result + sjxm;
        result = 31 * result + xmmc.hashCode();
        return result;
		//return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this == obj){
			return true;
		}
		
		if(!(obj instanceof XtcdVo)){
			return false;
		}
		
		XtcdVo vo = (XtcdVo) obj;
		
		return vo.xmdh == xmdh
				&& vo.xmdz.equals(xmdz)
				&& vo.xmmc.equals(xmmc)
				&& vo.sjxm ==sjxm;
		
		//return super.equals(obj);
	}
	*/
	

}
