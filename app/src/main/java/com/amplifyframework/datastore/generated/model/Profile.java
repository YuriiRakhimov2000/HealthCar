package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Profile type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Profiles")
public final class Profile implements Model {
  public static final QueryField ID = field("Profile", "id");
  public static final QueryField MAKE = field("Profile", "make");
  public static final QueryField MODEL = field("Profile", "model");
  public static final QueryField YEAR = field("Profile", "year");
  public static final QueryField SUB = field("Profile", "sub");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String make;
  private final @ModelField(targetType="String") String model;
  private final @ModelField(targetType="String") String year;
  private final @ModelField(targetType="String", isRequired = true) String sub;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getMake() {
      return make;
  }
  
  public String getModel() {
      return model;
  }
  
  public String getYear() {
      return year;
  }
  
  public String getSub() {
      return sub;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Profile(String id, String make, String model, String year, String sub) {
    this.id = id;
    this.make = make;
    this.model = model;
    this.year = year;
    this.sub = sub;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Profile profile = (Profile) obj;
      return ObjectsCompat.equals(getId(), profile.getId()) &&
              ObjectsCompat.equals(getMake(), profile.getMake()) &&
              ObjectsCompat.equals(getModel(), profile.getModel()) &&
              ObjectsCompat.equals(getYear(), profile.getYear()) &&
              ObjectsCompat.equals(getSub(), profile.getSub()) &&
              ObjectsCompat.equals(getCreatedAt(), profile.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), profile.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getMake())
      .append(getModel())
      .append(getYear())
      .append(getSub())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Profile {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("make=" + String.valueOf(getMake()) + ", ")
      .append("model=" + String.valueOf(getModel()) + ", ")
      .append("year=" + String.valueOf(getYear()) + ", ")
      .append("sub=" + String.valueOf(getSub()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static MakeStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static Profile justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Profile(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      make,
      model,
      year,
      sub);
  }
  public interface MakeStep {
    SubStep make(String make);
  }
  

  public interface SubStep {
    BuildStep sub(String sub);
  }
  

  public interface BuildStep {
    Profile build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep model(String model);
    BuildStep year(String year);
  }
  

  public static class Builder implements MakeStep, SubStep, BuildStep {
    private String id;
    private String make;
    private String sub;
    private String model;
    private String year;
    @Override
     public Profile build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Profile(
          id,
          make,
          model,
          year,
          sub);
    }
    
    @Override
     public SubStep make(String make) {
        Objects.requireNonNull(make);
        this.make = make;
        return this;
    }
    
    @Override
     public BuildStep sub(String sub) {
        Objects.requireNonNull(sub);
        this.sub = sub;
        return this;
    }
    
    @Override
     public BuildStep model(String model) {
        this.model = model;
        return this;
    }
    
    @Override
     public BuildStep year(String year) {
        this.year = year;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String make, String model, String year, String sub) {
      super.id(id);
      super.make(make)
        .sub(sub)
        .model(model)
        .year(year);
    }
    
    @Override
     public CopyOfBuilder make(String make) {
      return (CopyOfBuilder) super.make(make);
    }
    
    @Override
     public CopyOfBuilder sub(String sub) {
      return (CopyOfBuilder) super.sub(sub);
    }
    
    @Override
     public CopyOfBuilder model(String model) {
      return (CopyOfBuilder) super.model(model);
    }
    
    @Override
     public CopyOfBuilder year(String year) {
      return (CopyOfBuilder) super.year(year);
    }
  }
  
}
