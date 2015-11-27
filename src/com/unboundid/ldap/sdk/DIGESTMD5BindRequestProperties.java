/*
 * Copyright 2014-2015 UnboundID Corp.
 * All Rights Reserved.
 */
/*
 * Copyright (C) 2014-2015 UnboundID Corp.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (GPLv2 only)
 * or the terms of the GNU Lesser General Public License (LGPLv2.1 only)
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 */
package com.unboundid.ldap.sdk;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.unboundid.asn1.ASN1OctetString;
import com.unboundid.util.Mutable;
import com.unboundid.util.StaticUtils;
import com.unboundid.util.ThreadSafety;
import com.unboundid.util.ThreadSafetyLevel;
import com.unboundid.util.Validator;



/**
 * This class provides a data structure that may be used to hold a number of
 * properties that may be used during processing for a SASL DIGEST-MD5 bind
 * operation.
 */
@Mutable()
@ThreadSafety(level=ThreadSafetyLevel.NOT_THREADSAFE)
public final class DIGESTMD5BindRequestProperties
       implements Serializable
{
  /**
   * The serial version UID for this serializable class.
   */
  private static final long serialVersionUID = -2000440962628192477L;



  // The password for the DIGEST-MD5 bind request.
  private ASN1OctetString password;

  // The SASL quality of protection value(s) allowed for the DIGEST-MD5 bind
  // request.
  private List<SASLQualityOfProtection> allowedQoP;

  // The authentication ID string for the DIGEST-MD5 bind request.
  private String authenticationID;

  // The authorization ID string for the DIGEST-MD5 bind request, if available.
  private String authorizationID;

  // The realm for the DIGEST-MD5 bind request, if available.
  private String realm;



  /**
   * Creates a new set of DIGEST-MD5 bind request properties with the provided
   * information.
   *
   * @param  authenticationID  The authentication ID for the DIGEST-MD5 bind
   *                           request.  It must not be {@code null}.
   * @param  password          The password for the DIGEST-MD5 bind request.  It
   *                           may be {@code null} if anonymous authentication
   *                           is to be performed.
   */
  public DIGESTMD5BindRequestProperties(final String authenticationID,
                                        final String password)
  {
    this(authenticationID, new ASN1OctetString(password));
  }



  /**
   * Creates a new set of DIGEST-MD5 bind request properties with the provided
   * information.
   *
   * @param  authenticationID  The authentication ID for the DIGEST-MD5 bind
   *                           request.  It must not be {@code null}.
   * @param  password          The password for the DIGEST-MD5 bind request.  It
   *                           may be {@code null} if anonymous authentication
   *                           is to be performed.
   */
  public DIGESTMD5BindRequestProperties(final String authenticationID,
                                        final byte[] password)
  {
    this(authenticationID, new ASN1OctetString(password));
  }



  /**
   * Creates a new set of DIGEST-MD5 bind request properties with the provided
   * information.
   *
   * @param  authenticationID  The authentication ID for the DIGEST-MD5 bind
   *                           request.  It must not be {@code null}.
   * @param  password          The password for the DIGEST-MD5 bind request.  It
   *                           may be {@code null} if anonymous authentication
   *                           is to be performed.
   */
  public DIGESTMD5BindRequestProperties(final String authenticationID,
                                        final ASN1OctetString password)
  {
    Validator.ensureNotNull(authenticationID);

    this.authenticationID = authenticationID;

    if (password == null)
    {
      this.password = new ASN1OctetString();
    }
    else
    {
      this.password = password;
    }

    authorizationID = null;
    realm           = null;
    allowedQoP      = Collections.unmodifiableList(Arrays.asList(
         SASLQualityOfProtection.AUTH));
  }



  /**
   * Retrieves the authentication ID for the DIGEST-MD5 bind request.
   *
   * @return  The authentication ID for the DIGEST-MD5 bind request.
   */
  public String getAuthenticationID()
  {
    return authenticationID;
  }



  /**
   * Specifies the authentication ID for the DIGEST-MD5 bind request.  It must
   * not be {@code null}, and should generally start with "dn:" followed by the
   * full DN for the target user (or just "dn:" for anonymous), or "u:" followed
   * by the username for the target user.
   *
   * @param  authenticationID  The authentication ID for the DIGEST-MD5 bind
   *                           request.  It must not be {@code null}.
   */
  public void setAuthenticationID(final String authenticationID)
  {
    Validator.ensureNotNull(authenticationID);
    this.authenticationID = authenticationID;
  }



  /**
   * Retrieves the authorization ID for the DIGEST-MD5 bind request.
   *
   * @return  The authorization ID for the DIGEST-MD5 bind request, or
   *          {@code null} if no authorization ID should be included in the
   *          bind request.
   */
  public String getAuthorizationID()
  {
    return authorizationID;
  }



  /**
   * Specifies the authorization ID for the DIGEST-MD5 bind request.  It may be
   * {@code null} if not alternate authorization identity is needed.  If
   * provided, the authorization ID should generally start with "dn:" followed
   * by the full DN for the target user (or just "dn:" for anonymous), or "u:"
   * followed by the username for the target user.
   *
   * @param  authorizationID  The authorization ID for the DIGEST-MD5 bind
   *                          request.
   */
  public void setAuthorizationID(final String authorizationID)
  {
    this.authorizationID = authorizationID;
  }



  /**
   * Retrieves the password for the DIGEST-MD5 bind request.
   *
   * @return  The password for the DIGEST-MD5 bind request.
   */
  public ASN1OctetString getPassword()
  {
    return password;
  }



  /**
   * Specifies the password for the DIGEST-MD5 bind request.  It may be
   * {@code null} or empty when authenticating as the anonymous user.
   *
   * @param  password  The password for the DIGEST-MD5 bind request.  It may be
   *                   {@code null} or empty when authenticating as the
   *                   anonymous user.
   */
  public void setPassword(final String password)
  {
    setPassword(new ASN1OctetString(password));
  }



  /**
   * Specifies the password for the DIGEST-MD5 bind request.  It may be
   * {@code null} or empty when authenticating as the anonymous user.
   *
   * @param  password  The password for the DIGEST-MD5 bind request.  It may be
   *                   {@code null} or empty when authenticating as the
   *                   anonymous user.
   */
  public void setPassword(final byte[] password)
  {
    setPassword(new ASN1OctetString(password));
  }



  /**
   * Specifies the password for the DIGEST-MD5 bind request.  It may be
   * {@code null} or empty when authenticating as the anonymous user.
   *
   * @param  password  The password for the DIGEST-MD5 bind request.  It may be
   *                   {@code null} or empty when authenticating as the
   *                   anonymous user.
   */
  public void setPassword(final ASN1OctetString password)
  {
    if (password == null)
    {
      this.password = new ASN1OctetString();
    }
    else
    {
      this.password = password;
    }
  }




  /**
   * Retrieves the realm for the DIGEST-MD5 bind request.
   *
   * @return  The realm for the DIGEST-MD5 bind request, or {@code null} if no
   *          realm should be included in the bind request.
   */
  public String getRealm()
  {
    return realm;
  }



  /**
   * Specifies the realm for the DIGEST-MD5 bind request.  It may be
   * {@code null} if no realm should be included in the bind request.
   *
   * @param  realm  The realm for the DIGEST-MD5 bind request.  It may be
   *                {@code null} if no realm should be included in the bind
   *                request.
   */
  public void setRealm(final String realm)
  {
    this.realm = realm;
  }



  /**
   * Retrieves the list of allowed qualities of protection that may be used for
   * communication that occurs on the connection after the authentication has
   * completed, in order from most preferred to least preferred.
   *
   * @return  The list of allowed qualities of protection that may be used for
   *          communication that occurs on the connection after the
   *          authentication has completed, in order from most preferred to
   *          least preferred.
   */
  public List<SASLQualityOfProtection> getAllowedQoP()
  {
    return allowedQoP;
  }



  /**
   * Specifies the list of allowed qualities of protection that may be used for
   * communication that occurs on the connection after the authentication has
   * completed, in order from most preferred to least preferred.
   *
   * @param  allowedQoP  The list of allowed qualities of protection that may be
   *                     used for communication that occurs on the connection
   *                     after the authentication has completed, in order from
   *                     most preferred to least preferred.  If this is
   *                     {@code null} or empty, then a list containing only the
   *                     {@link SASLQualityOfProtection#AUTH} quality of
   *                     protection value will be used.
   */
  public void setAllowedQoP(final List<SASLQualityOfProtection> allowedQoP)
  {
    if ((allowedQoP == null) || allowedQoP.isEmpty())
    {
      this.allowedQoP = Collections.unmodifiableList(Arrays.asList(
           SASLQualityOfProtection.AUTH));
    }
    else
    {
      this.allowedQoP = Collections.unmodifiableList(
           new ArrayList<SASLQualityOfProtection>(allowedQoP));
    }
  }



  /**
   * Specifies the list of allowed qualities of protection that may be used for
   * communication that occurs on the connection after the authentication has
   * completed, in order from most preferred to least preferred.
   *
   * @param  allowedQoP  The list of allowed qualities of protection that may be
   *                     used for communication that occurs on the connection
   *                     after the authentication has completed, in order from
   *                     most preferred to least preferred.  If this is
   *                     {@code null} or empty, then a list containing only the
   *                     {@link SASLQualityOfProtection#AUTH} quality of
   *                     protection value will be used.
   */
  public void setAllowedQoP(final SASLQualityOfProtection... allowedQoP)
  {
    setAllowedQoP(StaticUtils.toList(allowedQoP));
  }



  /**
   * Retrieves a string representation of the DIGEST-MD5 bind request
   * properties.
   *
   * @return  A string representation of the DIGEST-MD5 bind request properties.
   */
  @Override()
  public String toString()
  {
    final StringBuilder buffer = new StringBuilder();
    toString(buffer);
    return buffer.toString();
  }



  /**
   * Appends a string representation of the DIGEST-MD5 bind request properties
   * to the provided buffer.
   *
   * @param  buffer  The buffer to which the information should be appended.
   */
  public void toString(final StringBuilder buffer)
  {
    buffer.append("DIGESTMD5BindRequestProperties(authenticationID='");
    buffer.append(authenticationID);
    buffer.append('\'');

    if (authorizationID != null)
    {
      buffer.append(", authorizationID='");
      buffer.append(authorizationID);
      buffer.append('\'');
    }

    if (realm != null)
    {
      buffer.append(", realm='");
      buffer.append(realm);
      buffer.append('\'');
    }

    buffer.append(", qop='");
    buffer.append(SASLQualityOfProtection.toString(allowedQoP));
    buffer.append("')");
  }
}
