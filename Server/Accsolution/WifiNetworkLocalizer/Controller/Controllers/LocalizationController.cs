using AutoMapper;
using Controller;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Http;
using WifiNetworkLocalizer.Model.Database_Handlers;

namespace WifiNetworkLocalizer.Controller
{
    [RoutePrefix("localization")]
    public class LocalizationController : ApiController
    {
        private ILocalization _localizationServices;
        private IMapper _mapperServices;

        public LocalizationController(ILocalization localizationServices, IMapper mapperServices)
        {
            _localizationServices = localizationServices;
            _mapperServices = mapperServices;
        }

        [HttpGet]
        [Route("point")] //?firstMacId=xxx&secondMacId=yyy&thirsMacId=zzz
        public IHttpActionResult GetXYLocalizationPoint
            ([FromUri] string firstMacId, [FromUri] string secondMacId, [FromUri] string thirdMacId)
        {
            var request = new Model.Message_Types.ThreeMacIds
            {
                FirstMacId = firstMacId,
                SecondMacId = secondMacId,
                ThirdMacId = thirdMacId
            };

            var data = _localizationServices.GetXYLocalizationPoint(request);

            return Ok(data);
        }

        [HttpGet]
        [Route("measurmentIds")] 
        public IHttpActionResult GetThreeMeasurmentMacIds()
        {
            var data = _localizationServices.GetThreeMeasurmentMacIds();

            ThreeMacIds result = 
                (data == null) ? null : _mapperServices.Map<Model.Message_Types.ThreeMacIds, ThreeMacIds>(data);

            return Ok(result);
        }

        [HttpPost]
        [Route("measutmentIds")]
        public IHttpActionResult SetThreeMeasurmentMacIds([FromBody] ThreeMacIds threeMacIds)
        {
            var request = _mapperServices.Map<ThreeMacIds, Model.Message_Types.ThreeMacIds>(threeMacIds);
            _localizationServices.SetThreeMeasurmentMacIds(request);

            return Ok();
        }

        [HttpGet]
        [Route("buildings")]
        public IHttpActionResult GetPossibleBuildings()
        {
            var data = _localizationServices.GetPossibleBuildings();

            return Ok(data);
        }
    }
}
