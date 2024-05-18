type ResponseType = {
  code?: number;
  message?: string;
  res: any;
  data?: any;
};

const sendSuccessResponse = ({
  res,
  message = "SUCCESS",
  data = {},
  code = 200,
}: ResponseType) => {
  return res.status(code).json({ message, data, status: code });
};

const sendErrorResponse = ({
  res,
  message = "INTERNAL_SERVER_ERROR",
  data = {},
  code = 500,
}: ResponseType) => {
  return res.status(code).json({ message, data, status: code });
};

export { sendSuccessResponse, sendErrorResponse };
