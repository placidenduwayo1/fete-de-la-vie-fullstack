package com.fdlv.gmd.api.mapper.forum;

/**
 * Cette interface présente des contratas pour mapper les dto vers les entities
 * et versa
 * 
 * @author JINHOGATE
 *
 * @param <D>
 * @param <E>
 */
public interface EntityMapper<D, E> {

	D entityToDto(E e);

	E dtoToEntity(D d);

}
