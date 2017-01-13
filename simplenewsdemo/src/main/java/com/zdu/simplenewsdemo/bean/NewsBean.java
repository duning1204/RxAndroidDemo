package com.zdu.simplenewsdemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duzongning on 2016/8/10.
 */
public class NewsBean implements Parcelable {

    /**
     * postid : PHOT22RHT000100A
     * hasCover : false
     * hasHead : 1
     * replyCount : 10516
     * hasImg : 1
     * digest :
     * hasIcon : false
     * docid : 9IG74V5H00963VRO_BU3FNJ71bjlongfangupdateDoc
     * title : 英国模特冠军耗时7个月将公厕改豪宅
     * order : 1
     * priority : 321
     * lmodify : 2016-08-10 12:53:30
     * boardid : photoview_bbs
     * ads : [{"title":"游客夜游大连海边 留下垃圾遍地","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/88a4272be3ce43f7ab778c64fc7ed86620160810094032.jpeg","subtitle":"","url":"00AP0001|2190941"},{"title":"广西七夕民众江中游泳 万人同游七姐水","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/3e3bdb3ba86e488593a3253da853da8320160810093911.jpeg","subtitle":"","url":"00AP0001|2190939"},{"title":"合肥一村镇捕杀焚烧26只疑似狂犬病狗","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/9478153cb5764849a759b79ef861e00620160810075804.jpeg","subtitle":"","url":"00AP0001|2190921"},{"title":"中国游客与加州警方上演\"生死追逐\"","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/7f87e4ce4f6840da96650631b63780a820160810074856.jpeg","subtitle":"","url":"00AO0001|2190911"},{"title":"济南黑虎泉又现\"泡脚客\" 保安成\"摆设\"","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/156424454ad54acb948c073c17857d2120160810074400.jpeg","subtitle":"","url":"00AP0001|2190916"}]
     * photosetID : 00AO0001|2190909
     * template : normal1
     * votecount : 9773
     * skipID : 00AO0001|2190909
     * alias : Top News
     * skipType : photoset
     * cid : C1348646712614
     * hasAD : 1
     * imgextra : [{"imgsrc":"http://cms-bucket.nosdn.127.net/2ac3c2238dad41c6a7fe554bbc827bbb20160810075004.jpeg"},{"imgsrc":"http://cms-bucket.nosdn.127.net/2816687278734b15aa34d0f1e8266fac20160810075001.jpeg"}]
     * source : 网易原创
     * ename : androidnews
     * imgsrc : http://cms-bucket.nosdn.127.net/ad57189cb3bc4000855c96b140b4666320160810074931.jpeg
     * tname : 头条
     * ptime : 2016-08-10 07:49:02
     */

    private ArrayList<NewsEntity> T1348647909107;//头条
    private ArrayList<NewsEntity> T1348649145984;//NBA
    private ArrayList<NewsEntity> T1348654060988;//汽车
    private ArrayList<NewsEntity> T1350383429665;//笑话


    public ArrayList<NewsEntity> getT1348654060988() {
        return T1348654060988;
    }

    public void setT1348654060988(ArrayList<NewsEntity> t1348654060988) {
        T1348654060988 = t1348654060988;
    }

    public ArrayList<NewsEntity> getT1350383429665() {
        return T1350383429665;
    }

    public void setT1350383429665(ArrayList<NewsEntity> t1350383429665) {
        T1350383429665 = t1350383429665;
    }

    public ArrayList<NewsEntity> getT1348649145984() {
        return T1348649145984;
    }

    public void setT1348649145984(ArrayList<NewsEntity> t1348649145984) {
        T1348649145984 = t1348649145984;
    }

    public ArrayList<NewsEntity> getT1348647909107() {
        return T1348647909107;
    }

    public void setT1348647909107(ArrayList<NewsEntity> T1348647909107) {
        this.T1348647909107 = T1348647909107;
    }

    public static class NewsEntity implements Parcelable {

        private String postid;
        private boolean hasCover;
        private int hasHead;
        private int replyCount;
        private int hasImg;
        private String digest;
        private boolean hasIcon;
        private String docid;
        private String title;
        private int order;
        private int priority;
        private String lmodify;
        private String boardid;
        private String photosetID;
        private String template;
        private int votecount;
        private String skipID;
        private String alias;
        private String skipType;
        private String cid;
        private int hasAD;
        private String source;
        private String ename;
        private String imgsrc;
        private String tname;
        private String ptime;
        /**
         * title : 游客夜游大连海边 留下垃圾遍地
         * tag : photoset
         * imgsrc : http://cms-bucket.nosdn.127.net/88a4272be3ce43f7ab778c64fc7ed86620160810094032.jpeg
         * subtitle :
         * url : 00AP0001|2190941
         */

        private List<AdsEntity> ads;
        /**
         * imgsrc : http://cms-bucket.nosdn.127.net/2ac3c2238dad41c6a7fe554bbc827bbb20160810075004.jpeg
         */

        private List<ImgextraEntity> imgextra;

        public String getPostid() {
            return postid;
        }

        public void setPostid(String postid) {
            this.postid = postid;
        }

        public boolean isHasCover() {
            return hasCover;
        }

        public void setHasCover(boolean hasCover) {
            this.hasCover = hasCover;
        }

        public int getHasHead() {
            return hasHead;
        }

        public void setHasHead(int hasHead) {
            this.hasHead = hasHead;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public int getHasImg() {
            return hasImg;
        }

        public void setHasImg(int hasImg) {
            this.hasImg = hasImg;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public boolean isHasIcon() {
            return hasIcon;
        }

        public void setHasIcon(boolean hasIcon) {
            this.hasIcon = hasIcon;
        }

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getLmodify() {
            return lmodify;
        }

        public void setLmodify(String lmodify) {
            this.lmodify = lmodify;
        }

        public String getBoardid() {
            return boardid;
        }

        public void setBoardid(String boardid) {
            this.boardid = boardid;
        }

        public String getPhotosetID() {
            return photosetID;
        }

        public void setPhotosetID(String photosetID) {
            this.photosetID = photosetID;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public int getVotecount() {
            return votecount;
        }

        public void setVotecount(int votecount) {
            this.votecount = votecount;
        }

        public String getSkipID() {
            return skipID;
        }

        public void setSkipID(String skipID) {
            this.skipID = skipID;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getSkipType() {
            return skipType;
        }

        public void setSkipType(String skipType) {
            this.skipType = skipType;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public int getHasAD() {
            return hasAD;
        }

        public void setHasAD(int hasAD) {
            this.hasAD = hasAD;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public List<AdsEntity> getAds() {
            return ads;
        }

        public void setAds(List<AdsEntity> ads) {
            this.ads = ads;
        }

        public List<ImgextraEntity> getImgextra() {
            return imgextra;
        }

        public void setImgextra(List<ImgextraEntity> imgextra) {
            this.imgextra = imgextra;
        }

        public static class AdsEntity implements Parcelable {

            private String title;
            private String tag;
            private String imgsrc;
            private String subtitle;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getImgsrc() {
                return imgsrc;
            }

            public void setImgsrc(String imgsrc) {
                this.imgsrc = imgsrc;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.title);
                dest.writeString(this.tag);
                dest.writeString(this.imgsrc);
                dest.writeString(this.subtitle);
                dest.writeString(this.url);
            }

            public AdsEntity() {
            }

            protected AdsEntity(Parcel in) {
                this.title = in.readString();
                this.tag = in.readString();
                this.imgsrc = in.readString();
                this.subtitle = in.readString();
                this.url = in.readString();
            }

            public static final Creator<AdsEntity> CREATOR = new Creator<AdsEntity>() {
                @Override
                public AdsEntity createFromParcel(Parcel source) {
                    return new AdsEntity(source);
                }

                @Override
                public AdsEntity[] newArray(int size) {
                    return new AdsEntity[size];
                }
            };
        }

        public static class ImgextraEntity implements Parcelable {


            private String imgsrc;

            public String getImgsrc() {
                return imgsrc;
            }

            public void setImgsrc(String imgsrc) {
                this.imgsrc = imgsrc;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.imgsrc);
            }

            public ImgextraEntity() {
            }

            protected ImgextraEntity(Parcel in) {
                this.imgsrc = in.readString();
            }

            public static final Creator<ImgextraEntity> CREATOR = new Creator<ImgextraEntity>() {
                @Override
                public ImgextraEntity createFromParcel(Parcel source) {
                    return new ImgextraEntity(source);
                }

                @Override
                public ImgextraEntity[] newArray(int size) {
                    return new ImgextraEntity[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.postid);
            dest.writeByte(this.hasCover ? (byte) 1 : (byte) 0);
            dest.writeInt(this.hasHead);
            dest.writeInt(this.replyCount);
            dest.writeInt(this.hasImg);
            dest.writeString(this.digest);
            dest.writeByte(this.hasIcon ? (byte) 1 : (byte) 0);
            dest.writeString(this.docid);
            dest.writeString(this.title);
            dest.writeInt(this.order);
            dest.writeInt(this.priority);
            dest.writeString(this.lmodify);
            dest.writeString(this.boardid);
            dest.writeString(this.photosetID);
            dest.writeString(this.template);
            dest.writeInt(this.votecount);
            dest.writeString(this.skipID);
            dest.writeString(this.alias);
            dest.writeString(this.skipType);
            dest.writeString(this.cid);
            dest.writeInt(this.hasAD);
            dest.writeString(this.source);
            dest.writeString(this.ename);
            dest.writeString(this.imgsrc);
            dest.writeString(this.tname);
            dest.writeString(this.ptime);
            dest.writeList(this.ads);
            dest.writeList(this.imgextra);
        }

        public NewsEntity() {
        }

        protected NewsEntity(Parcel in) {
            this.postid = in.readString();
            this.hasCover = in.readByte() != 0;
            this.hasHead = in.readInt();
            this.replyCount = in.readInt();
            this.hasImg = in.readInt();
            this.digest = in.readString();
            this.hasIcon = in.readByte() != 0;
            this.docid = in.readString();
            this.title = in.readString();
            this.order = in.readInt();
            this.priority = in.readInt();
            this.lmodify = in.readString();
            this.boardid = in.readString();
            this.photosetID = in.readString();
            this.template = in.readString();
            this.votecount = in.readInt();
            this.skipID = in.readString();
            this.alias = in.readString();
            this.skipType = in.readString();
            this.cid = in.readString();
            this.hasAD = in.readInt();
            this.source = in.readString();
            this.ename = in.readString();
            this.imgsrc = in.readString();
            this.tname = in.readString();
            this.ptime = in.readString();
            this.ads = new ArrayList<AdsEntity>();
            in.readList(this.ads, AdsEntity.class.getClassLoader());
            this.imgextra = new ArrayList<ImgextraEntity>();
            in.readList(this.imgextra, ImgextraEntity.class.getClassLoader());
        }

        public static final Parcelable.Creator<NewsEntity> CREATOR = new Parcelable.Creator<NewsEntity>() {
            @Override
            public NewsEntity createFromParcel(Parcel source) {
                return new NewsEntity(source);
            }

            @Override
            public NewsEntity[] newArray(int size) {
                return new NewsEntity[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.T1348647909107);
        dest.writeTypedList(this.T1348649145984);
        dest.writeTypedList(this.T1348654060988);
        dest.writeTypedList(this.T1350383429665);
    }

    public NewsBean() {
    }

    protected NewsBean(Parcel in) {
        this.T1348647909107 = in.createTypedArrayList(NewsEntity.CREATOR);
        this.T1348649145984 = in.createTypedArrayList(NewsEntity.CREATOR);
        this.T1348654060988 = in.createTypedArrayList(NewsEntity.CREATOR);
        this.T1350383429665 = in.createTypedArrayList(NewsEntity.CREATOR);
    }

    public static final Parcelable.Creator<NewsBean> CREATOR = new Parcelable.Creator<NewsBean>() {
        @Override
        public NewsBean createFromParcel(Parcel source) {
            return new NewsBean(source);
        }

        @Override
        public NewsBean[] newArray(int size) {
            return new NewsBean[size];
        }
    };
}
