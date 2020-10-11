package greencity.dto.habitstatistic;

import greencity.constant.ValidationConstants;
import greencity.entity.enums.HabitRate;
import java.time.ZonedDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class AddHabitStatisticDto {
    @Range(min = ValidationConstants.MIN_AMOUNT_OF_ITEMS, max = ValidationConstants.MAX_AMOUNT_OF_ITEMS)
    @NotNull
    private Integer amountOfItems;
    @NotNull
    private HabitRate habitRate;
    @NotNull
    private ZonedDateTime createDate;
    @Min(0)
    @NotNull
    private Long habitAssignId;
}
