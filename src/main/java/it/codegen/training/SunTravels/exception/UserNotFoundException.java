/*
 * Copyright (c) 2023. CodeGen International (Pvt) Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of CodeGen
 * International (Pvt) Ltd. ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with CodeGen International.
 *
 */
package it.codegen.training.SunTravels.exception;

/**
 * <b>Description Title</b>
 * Description Text.
 *
 * @author wasanapa
 * @since 18 Apr 2023
 */
public class UserNotFoundException extends RuntimeException
{
    public UserNotFoundException( String s )
    {
        super(s);
    }
}
