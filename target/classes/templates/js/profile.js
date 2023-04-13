function getProfileCompletionStatus() {
  const numCompletedFields = 5; // replace this with the actual number of completed fields
  const totalFields = 10; // replace this with the total number of fields in the profile

  const percentage = (numCompletedFields / totalFields) * 100;
  return Math.round(percentage);
}
