/*
 * TaskUnifier
 * Copyright (c) 2011, Benjamin Leclerc
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of TaskUnifier or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package morutask.models;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.leclercb.commons.api.utils.CheckUtils;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import morutask.converters.ModelIdConverter;

@XStreamConverter(ModelIdConverter.class)
public class ModelId implements Serializable, Comparable<ModelId> {
	
	private boolean newId;
	private String id;
	
	public ModelId() {
		this(true, UUID.randomUUID().toString());
	}
	
	public ModelId(String id) {
		this(false, id);
	}
	
	public ModelId(boolean newId, String id) {
		this.setNewId(newId);
		this.setId(id);
	}
	
	public boolean isNewId() {
		return this.newId;
	}
	
	private void setNewId(boolean newId) {
		this.newId = newId;
	}
	
	public String getId() {
		return this.id;
	}
	
	private void setId(String id) {
		CheckUtils.hasContent(id);
		this.id = id;
	}
	
	@Override
	public String toString() {
		if (this.newId)
			return "NEW ID (" + this.id + ")";
		
		return this.id;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		
		if (o instanceof ModelId) {
			ModelId id = (ModelId) o;
			
			return new EqualsBuilder().append(this.newId, id.newId).append(
					this.id,
					id.id).isEquals();
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.newId).append(this.id).toHashCode();
	}
	
	@Override
	public int compareTo(ModelId modelId) {
		if (this.id == null)
			return 1;
		
		if (this.isNewId() && modelId.isNewId())
			return this.id.compareTo(modelId.id);
		
		if (!this.isNewId() && !modelId.isNewId())
			return this.id.compareTo(modelId.id);
		
		if (this.isNewId())
			return -1;
		
		return 1;
	}
	
}
