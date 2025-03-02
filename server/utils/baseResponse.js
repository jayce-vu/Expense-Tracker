const baseResponse = (status, message, data = null, error = null) => {
    return { status, message, data, error };
  };
  
  module.exports = baseResponse;
  