/**
 * Copyright (c) 2009, Gareth Bond, http://www.gazbond.co.uk
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided
 * that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice, this list of conditions and the
 *     following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and
 *     the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package gizmo.uk.toolkit.rpc.handlers;

import gizmo.uk.toolkit.rpc.RpcContext;
import gizmo.uk.toolkit.rpc.RpcError;
import gizmo.uk.toolkit.rpc.RpcTargetHandler;

import javax.servlet.http.HttpSession;

/**
 * <p>SessionScopeTargetHandler stores a service object for each users session</p>
 *
 * <p>This is done using HttpSession</p>
 *
 * <p>Note that the service objects toString() method is used for the session key</p>
 * 
 * @author gareth bond
 */
public class SessionScopeTargetHandler implements RpcTargetHandler {

    public Object getTarget(RpcContext context) {
        HttpSession session = context.getRequest().getSession();
        Object target = session.getAttribute(toString());
        if(target == null) {
            try {
                target = context.getServiceClass().newInstance();
            }
            catch(Exception e) {
                throw new RpcError(e);
            }
            session.setAttribute(toString(), target);
        }
        return target;
    }

}
