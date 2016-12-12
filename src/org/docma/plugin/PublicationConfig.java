/*
 * PublicationConfig.java
 *
 *  Copyright (C) 2016  Manfred Paula, http://www.docmenta.org
 *   
 *  This file is part of Docmenta. Docmenta is free software: you can 
 *  redistribute it and/or modify it under the terms of the GNU Lesser 
 *  General Public License as published by the Free Software Foundation, 
 *  either version 3 of the License, or (at your option) any later version.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Docmenta.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.docma.plugin;

/**
 *
 * @author MP
 */
public interface PublicationConfig 
{
    String getId();

    String getContentRoot();
    void setContentRoot(String contentRoot);
    
    String getFilterSetting();
    String[] getFilterSettingApplics();
    void setFilterSetting(String filterSetting);
    
    String getReferencedPubs();
    String[] getReferencedPubIds();
    void setReferencedPubs(String id_list);
    
    String getTitle();
    void setTitle(String title);
    
    String getAppendixRoot();
    void setAppendixRoot(String appendixRoot);
    
    String getAuthors();
    void setAuthors(String authors);
    
    String getCopyrightHolder();
    void setCopyrightHolder(String copyrightHolder);
    
    String getCopyrightYear();
    void setCopyrightYear(String copyrightYear);
    
    String getCorporate();
    void setCorporate(String corporate);
    
    String getCoverImageAlias();
    void setCoverImageAlias(String cover);
    
    String getCredit();
    void setCredit(String credit);

    String getCustomTitlePage1();
    void setCustomTitlePage1(String customTitlePage1);

    String getCustomTitlePage2();
    void setCustomTitlePage2(String customTitlePage2);

    String getLegalNotice();
    void setLegalNotice(String legalNotice);

    String getPrefaceRoot();
    void setPrefaceRoot(String prefaceRoot);

    String getPubAbstract();
    void setPubAbstract(String pubAbstract);

    String getPubDate();
    void setPubDate(String pubDate);

    String getReleaseInfo();
    void setReleaseInfo(String releaseInfo);

    String getSubtitle();
    void setSubtitle(String subtitle);

    String getPublisher();
    void setPublisher(String publisher);

    String getBiblioId();
    void setBiblioId(String bibid);

}
