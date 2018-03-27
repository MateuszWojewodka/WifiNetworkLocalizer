using Model.Database_Classes;
using Model.Entity_Classes;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WifiNetworkLocalizer.Model.Database_Entities;
using WifiNetworkLocalizer.Model.Message_Types;

namespace WifiNetworkLocalizer.Model.Database_Handlers
{
    public class Localization : ILocalization
    {
        public Point GetXYLocalizationPoint(DeterminantMacIds threeMacIds)
        {
            throw new NotImplementedException();
        }

        public List<RoomInfo> GetPossibleRooms()
        {
            using (var ctx = new WifiLocalizerContext())
            {
                List<RoomInfo> returnList = new List<RoomInfo>();

                var selectResult = ctx.DeterminantMacIds.Select(x => new
                {
                    RoomId = x.Id,
                    RoomName = x.RoomName
                });

                foreach (var item in selectResult)
                {
                    returnList.Add(new RoomInfo
                    {
                        roomId = item.RoomId,
                        roomName = item.RoomName
                    });
                }

                return returnList;
            }
        }

        public DeterminantMacIds GetThreeMeasurmentMacIds()
        {
            using (var ctx = new WifiLocalizerContext())
            {
                return ctx.DeterminantMacIds.FirstOrDefault();
            }
        }

        public void SetThreeMeasurmentMacIds(DeterminantMacIds threeMacIds)
        {
            using (var ctx = new WifiLocalizerContext())
            {
                //ctx.Database.ExecuteSqlCommand("DELETE FROM DeterminantMacIds"); //clear Table to keep always one record inside
                TryAddElementToDataBase(ctx, ctx.DeterminantMacIds, threeMacIds);
            }
        }

        public void AddRSSIMeasurmentInXYPoint(RSSIMeasurmentPoint RSSIMeasurmentPoint)
        {
            using (var ctx = new WifiLocalizerContext())
            {
                DeterminantMacIds determinantMacIds = ctx.DeterminantMacIds.Find(RSSIMeasurmentPoint.RefId);

                if (determinantMacIds == null)
                    throw new NullReferenceException($"There is no value with {RSSIMeasurmentPoint.RefId} id in database.");

                RSSIMeasurmentPoint.DeterminantMacIds = determinantMacIds;
                TryAddElementToDataBase(ctx, ctx.RSSIMeasurmentPoints, RSSIMeasurmentPoint);
            }
        }

        #region PRIVATE_METHODS

        private void TryAddElementToDataBase<ElementType>
            (DbContext ctx, DbSet<ElementType> dbSet, ElementType element) where ElementType : class
        {
            try
            {
                dbSet.Add(element);
                ctx.SaveChanges();

                PrintSuccesAddingToDatabaseMessage(element);
            }
            catch(Exception error)
            {
                PrintFailedAddingToDatabaseMessage(element, error.Message);

                throw error;
            }
        }

        private void PrintSuccesAddingToDatabaseMessage<T>(T elementToBeAdd)
        {
            Console.WriteLine($"Added {typeof(T).Name} element to database:");

            foreach (var property in typeof(T).GetProperties())
            {
                Console.WriteLine($"{property.Name} -> {property.GetValue(elementToBeAdd)}");
            }

            Console.WriteLine("\n");
        }

        private void PrintFailedAddingToDatabaseMessage<T>(T elementToBeAdd, string failExceptionMessage)
        {
            Console.ForegroundColor = ConsoleColor.Red;

            Console.WriteLine(failExceptionMessage);
            Console.WriteLine($"When trying to add {typeof(T).Name} element to databse:");

            Console.WriteLine(typeof(T).Name);
            foreach (var property in typeof(T).GetProperties())
            {
                Console.WriteLine($"{property.Name} -> {property.GetValue(elementToBeAdd)}");
            }

            Console.WriteLine("\n");

            Console.ResetColor();
        }

        Point ILocalization.GetXYLocalizationPoint(DeterminantMacIds threeMacIds)
        {
            throw new NotImplementedException();
        }

        #endregion
    }
}
