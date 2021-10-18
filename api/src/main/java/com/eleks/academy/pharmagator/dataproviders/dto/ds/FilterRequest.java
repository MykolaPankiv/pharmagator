package com.eleks.academy.pharmagator.dataproviders.dto.ds;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterRequest {

	private Long page;
	private Long per;

}
