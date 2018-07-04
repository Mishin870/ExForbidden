package com.mishin870.exforbidden.forestrycomp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import forestry.api.core.IErrorLogic;
import forestry.api.core.IErrorState;

/**
 * База всех источников "ошибок" ExForbidden.
 * Позволяет извне устанавливать состояния каждого сообщения об ошибке
 */
public class EFErrorLogic implements IErrorLogic {
	private final Set<IErrorState> errorStates = new HashSet();
	
	@Override
	public final boolean setCondition(boolean condition, IErrorState errorState) {
		if (condition) {
			this.errorStates.add(errorState);
		} else {
			this.errorStates.remove(errorState);
		}
		return condition;
	}
	
	@Override
	public final boolean contains(IErrorState state) {
		return this.errorStates.contains(state);
	}
	
	@Override
	public final boolean hasErrors() {
		return this.errorStates.size() > 0;
	}
	
	@Override
	public final ImmutableSet<IErrorState> getErrorStates() {
		return ImmutableSet.copyOf(this.errorStates);
	}
	
	@Override
	public void clearErrors() {
		this.errorStates.clear();
	}
	
	@Override
	public void writeData(DataOutputStream data) throws IOException {
		data.writeShort(this.errorStates.size());
		for (IErrorState errorState : this.errorStates) {
			data.writeShort(errorState.getID());
		}
	}
	
	@Override
	public void readData(DataInputStream data) throws IOException {
		clearErrors();
		short errorStateCount = data.readShort();
		for (int i = 0; i < errorStateCount; i++) {
			short errorStateId = data.readShort();
			IErrorState errorState = EFForestryErrors.getErrorState(errorStateId);
			this.errorStates.add(errorState);
		}
	}
}